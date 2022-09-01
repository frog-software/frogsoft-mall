package runners

import (
	"context"
	"fmt"
	"sync"
	"time"

	"apiserver/workqueue"

	"golang.org/x/sync/syncmap"
	"golang.org/x/time/rate"
)

const (
	maxRetryDelay = 1200 * time.Second
)

// Executor is a rate-limited work queue with concurrent workers.
type Executor struct {
	workers      int
	queueSize    int
	maxRetries   int
	allowRetries bool
	wg           sync.WaitGroup
	timeout      time.Duration
	runningJobs  sync.Map
	queue        workqueue.RateLimitingInterface
}

// Job is an Action to be executed by the workers in the Executor.
type Job interface {
	Type() string
	Run(ctx context.Context) error
	AllowConcurrency() bool
}

type Config struct {
	QueueSize            int
	Workers              int
	MaxJobRetries        int
	BaseRetryDelay       time.Duration
	RetryJobAfterFailure bool
	PerWorkerQPS         int
	Timeout              time.Duration
}

// New creates a new Executor with a queue size, number of workers,
// and a job-running or shutdown timeout.
func New(c Config) (*Executor, error) {
	if c.QueueSize == 0 || c.Workers == 0 || c.MaxJobRetries == 0 ||
		c.BaseRetryDelay == 0 || c.Timeout == 0 || c.PerWorkerQPS == 0 {
		return nil, fmt.Errorf("invalid executor config")
	}
	e := &Executor{}
	e.workers = c.Workers
	e.timeout = c.Timeout
	e.queueSize = c.QueueSize
	e.maxRetries = c.MaxJobRetries
	e.allowRetries = c.RetryJobAfterFailure
	e.wg = sync.WaitGroup{}
	e.runningJobs = syncmap.Map{}
	// Create a rate limited queue, with a token bucket for overall limiting,
	// and exponential failure for per-item limiting.
	e.queue = workqueue.NewRateLimitingQueue(
		workqueue.NewMaxOfRateLimiter(
			workqueue.NewItemExponentialFailureRateLimiter(c.BaseRetryDelay, maxRetryDelay),
			&workqueue.BucketRateLimiter{
				// Token Bucket limiter, with
				// qps = workers * qpsToWorkerRatio, maxBurst = queueSize
				Limiter: rate.NewLimiter(rate.Limit(c.Workers*c.PerWorkerQPS), c.QueueSize),
			},
		),
	)

	return e, nil
}

func (e *Executor) setJobStatus(j Job, status bool) {
	// TODO(charlie0129): we are simply use action type to prevent concurrent
	// runs. This is too strict. Ideally, we would the action hash/ID to do so.
	if status {
		e.runningJobs.Store(j.Type(), true)
	} else {
		e.runningJobs.Delete(j.Type())
	}
}

func (e *Executor) setJobRunning(j Job) {
	e.setJobStatus(j, true)
}

func (e *Executor) setJobNotRunning(j Job) {
	e.setJobStatus(j, false)
}

func (e *Executor) getJobStatus(j Job) bool {
	v, ok := e.runningJobs.Load(j.Type())
	if !ok {
		return false
	}
	return v.(bool)
}

func (e *Executor) requeueJob(j Job) {
	if e.queue.NumRequeues(j) < e.maxRetries {
		e.queue.AddRateLimited(j)
		return
	}
	e.queue.Forget(j)
}

// AddJob adds a job to the queue.
func (e *Executor) AddJob(j Job) error {
	if e.queue.Len() >= e.queueSize {
		return fmt.Errorf("queue full with size %d, cannot add job %s", e.queue.Len(), j.Type())
	}
	e.queue.Add(j)
	return nil
}

func (e *Executor) runJob(ctx context.Context) bool {
	item, quit := e.queue.Get()
	if quit {
		return false
	}

	defer e.queue.Done(item)

	j, ok := item.(Job)
	if !ok {
		return true
	}
	if j == nil {
		return true
	}

	// This job does not allow concurrent runs, and it is already running.
	// Requeue it to run it later.
	if !j.AllowConcurrency() && e.getJobStatus(j) {
		e.requeueJob(j)
		return true
	}

	// Add a job timeout
	timeoutCtx, cancel := context.WithDeadline(ctx, time.Now().Add(e.timeout))
	defer cancel()

	e.setJobRunning(j)
	err := j.Run(timeoutCtx)
	e.setJobNotRunning(j)

	if err == nil && timeoutCtx.Err() == nil {
		e.queue.Forget(j)
	} else {
		if e.allowRetries {
			e.requeueJob(j)
		}
	}

	return true
}

// RunJobs starts workers.
func (e *Executor) RunJobs(ctx context.Context) {
	e.wg.Add(e.workers)
	for i := 0; i < e.workers; i++ {
		go func() {
			for e.runJob(ctx) {
			}
			e.wg.Done()
		}()
	}
}

// Shutdown stops workers.
func (e *Executor) Shutdown() bool {

	// Wait for workers to end with a timeout.
	ch := make(chan struct{})
	go func() {
		e.queue.ShutDown()
		e.wg.Wait()
		close(ch)
	}()

	select {
	case <-ch:
		return true
	case <-time.After(e.timeout):
		return false
	}
}
