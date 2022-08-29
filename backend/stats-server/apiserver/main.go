package main

import (
	"apiserver/controllers"
	"fmt"
	"github.com/gin-gonic/gin"
	"os"
)

var router *gin.Engine

func CORS() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.Writer.Header().Set("Access-Control-Allow-Origin", "*")
		c.Writer.Header().Set("Access-Control-Allow-Credentials", "true")
		c.Writer.Header().Set("Access-Control-Allow-Headers", "Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization, accept, origin, Cache-Control, X-Requested-With")
		c.Writer.Header().Set("Access-Control-Allow-Methods", "POST, OPTIONS, GET, PUT, DELETE")

		if c.Request.Method == "OPTIONS" {
			c.AbortWithStatus(204)
			return
		}

		c.Next()
	}
}

func main() {
	router = gin.Default()

	// Enable CORS
	router.Use(CORS())

	// Register routes
	for _, r := range controllers.Controllers() {
		router.Handle(r.Method, r.Path, r.Handler)
	}

	err := router.Run("0.0.0.0:8080")
	if err != nil {
		_, _ = fmt.Fprint(os.Stderr, "Error: %w\n", err)
		return
	}
}
