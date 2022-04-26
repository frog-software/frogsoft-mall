package main

import (
	// "fmt"
	"frogsoftware/object-storage/providers/tencentcloud"
	"net/http"

	"github.com/gin-gonic/gin"
)

func main() {
	client := tencentcloud.NewClient()

	getObject := func(c *gin.Context) {

		// get the path
		fullPath := c.Request.URL.Path
		// generate a url pointing to COS
		cosUrl := tencentcloud.TranslateObjectUrl(fullPath, client)
		// redirect the traffic
		c.Redirect(http.StatusMovedPermanently, cosUrl)
	}

	router := gin.Default()
	router.Use(func(ctx *gin.Context) {
		switch ctx.Request.Method {
		case "GET":
			getObject(ctx)
		case "POST":
			ctx.Next()
		case "PUT":
			ctx.Next()
		case "DELETE":
			ctx.Next()
		default:
			ctx.Next()
		}
	})

	router.Run("localhost:8080")
}
