package runners

import (
	"fmt"
	"os"
	"os/exec"
	"path/filepath"
)

var (
	hdfs = "hdfs"
	// HDFS paths
	outputDir = "/root/output"
	inputDir  = "/root/input"
	// Local paths
	dataFile = filepath.Join("user_log_100k.csv")
)

var rm = []string{
	"dfs",
	"-rm",
	"-r",
	"-f",
}

var put = []string{
	"dfs",
	"-put",
	"-f",
}

var getmerge = []string{
	"dfs",
	"-getmerge",
}

var mkdir = []string{
	"dfs",
	"-mkdir",
	"-p",
}

func RunCmd(program string, args ...string) (*exec.Cmd, error) {
	cmd := exec.Command(program, args...)
	err := cmd.Run()
	return cmd, err
}

func CleanOutput() error {
	_, err := RunCmd(hdfs, append(rm, outputDir)...)
	return err
}

func CleanInput() error {
	_, err := RunCmd(hdfs, append(rm, inputDir)...)
	return err
}

func MkdirInput() error {
	_, err := RunCmd(hdfs, append(mkdir, inputDir)...)
	return err
}

func CopyToInput(file string) error {
	_, err := RunCmd(hdfs, append(put, file, inputDir)...)
	return err
}

func RunJar(jar, class string, args ...string) error {
	_, err := RunCmd("hadoop", append([]string{
		"jar",
		jar,
		class,
	}, args...)...)
	return err
}

func GetOutput(localPath string) error {
	_, err := RunCmd(hdfs, append(getmerge, outputDir, localPath)...)
	return err
}

func RunJob(job JobConfig) error {
	err := CleanInput()
	if err != nil {
		return fmt.Errorf("clean input failed, %w", err)
	}

	err = CleanOutput()
	if err != nil {
		return fmt.Errorf("clean output failed, %w", err)
	}

	err = MkdirInput()
	if err != nil {
		return fmt.Errorf("mkdir input failed, %w", err)
	}

	err = CopyToInput(dataFile)
	if err != nil {
		return fmt.Errorf("copy to input failed, %w", err)
	}

	err = RunJar(job.JarPath, job.MainClass, inputDir, outputDir)
	if err != nil {
		return fmt.Errorf("run jar failed, %w", err)
	}

	_ = os.RemoveAll(job.OutputPath)

	err = GetOutput(job.OutputPath)
	if err != nil {
		return fmt.Errorf("get output failed, %w", err)
	}

	return nil
}
