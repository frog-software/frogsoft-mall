package tencentcloud

import (
	"context"
	"fmt"
	"io"
	"net/http"
	"net/url"
	"os"

	"github.com/tencentyun/cos-go-sdk-v5"
)

func NewClient() *cos.Client {
	cosUrl := os.Getenv("COS_URL")
	cosSecretId := os.Getenv("COS_SECRET_ID")
	cosSecretKey := os.Getenv("COS_SECRET_KEY")

	// check empty environment variables
	if len(cosUrl) == 0 || len(cosSecretId) == 0 || len(cosSecretKey) == 0 {
		fmt.Fprintln(os.Stderr, "COS_URL, COS_SECRET_ID, COS_SECRET_KEY must be set")
		os.Exit(1)
	}

	// login
	u, _ := url.Parse(cosUrl)
	b := &cos.BaseURL{
		BucketURL: u,
	}
	c := cos.NewClient(b, &http.Client{
		Transport: &cos.AuthorizationTransport{
			SecretID:  cosSecretId,
			SecretKey: cosSecretKey,
		},
	})

	return c
}

// code style: 要把 context 放在前面，以后重构
func TranslateObjectUrl(objectId string, cosClient *cos.Client) string {
	return cosClient.BaseURL.BucketURL.String() + objectId
}

func UploadFile(name string, fd io.Reader, cosClient *cos.Client) error {
	_, err := cosClient.Object.Put(context.Background(), name, fd, nil)
	return err
}
