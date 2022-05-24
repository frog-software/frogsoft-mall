package tencentcloud

import (
	"context"
	"io"
	"net/http"
	"net/url"
	"os"

	"github.com/tencentyun/cos-go-sdk-v5"
)

func NewClient() *cos.Client {
	cosURL := os.Getenv("COS_URL")
	cosSecretID := os.Getenv("COS_SECRET_ID")
	cosSecretKey := os.Getenv("COS_SECRET_KEY")

	// check empty environment variables
	if len(cosURL) == 0 || len(cosSecretID) == 0 || len(cosSecretKey) == 0 {
		panic("COS_URL, COS_SECRET_ID, COS_SECRET_KEY must be set")
	}

	// login
	u, _ := url.Parse(cosURL)
	b := &cos.BaseURL{
		BucketURL: u,
	}
	c := cos.NewClient(b, &http.Client{
		Transport: &cos.AuthorizationTransport{
			SecretID:  cosSecretID,
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
