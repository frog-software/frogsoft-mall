package main

import (
	"frogsoftware/object-storage/providers/tencentcloud"
	"frogsoftware/object-storage/utils"
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

	putObject := func(c *gin.Context) {
		// get the path
		fullPath := c.Request.URL.Path
		// remove forward slash
		fullPath = utils.RemovePrefix("/", fullPath)
		// path must not be empty
		if len(fullPath) == 0 {
			c.String(http.StatusBadRequest, "Invalid Path")
			return
		}
		// prefix with 'mall/'
		fullPath = "mall/" + fullPath
		// single file
		file, _ := c.FormFile("file")

		fd, err := file.Open()
		if err != nil {
			c.String(http.StatusBadRequest, "Invalid File")
		}

		err = tencentcloud.UploadFile(fullPath, fd, client)

		if err != nil {
			c.String(http.StatusInternalServerError, "Upload Failed")
		}

		c.String(http.StatusCreated, c.Request.URL.Path)
	}

	router := gin.Default()
	router.Use(func(ctx *gin.Context) {
		switch ctx.Request.Method {
		case "GET":
			getObject(ctx)
		case "POST":
			ctx.Next()
		case "PUT":
			putObject(ctx)
		case "DELETE":
			ctx.Next()
		default:
			ctx.Next()
		}
	})

	router.Run("localhost:8080")
}
