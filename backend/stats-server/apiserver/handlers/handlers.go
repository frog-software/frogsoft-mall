package handlers

import (
	"apiserver/readers"
	"apiserver/runners"
	"github.com/gin-gonic/gin"
	"strings"
)

func GenderHandler(c *gin.Context) {
	res, err := readers.GenderReader()
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(200, res)
}

func GenderRunHandler(c *gin.Context) {
	err := runners.RunJob(runners.GenderJob)
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(201, nil)
}

func AgeHandler(c *gin.Context) {
	res, err := readers.AgeReader()
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(200, res)
}

func AgeRunHandler(c *gin.Context) {
	err := runners.RunJob(runners.AgeJob)
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(201, nil)
}

func ProvinceHandler(c *gin.Context) {
	res, err := readers.ProvinceReader()
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(200, res)
}

func ProvinceRunHandler(c *gin.Context) {
	err := runners.RunJob(runners.ProvinceJob)
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(201, nil)
}

func ActionHandler(c *gin.Context) {
	res, err := readers.ActionReader()
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(200, res)
}

func ActionRunHandler(c *gin.Context) {
	err := runners.RunJob(runners.ActionJob)
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(201, nil)
}

func CategoryHandler(c *gin.Context) {
	res, err := readers.CategoryReader()
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(200, res)
}

func CategoryRunHandler(c *gin.Context) {
	err := runners.RunJob(runners.CategoryJob)
	if err != nil {
		c.JSON(500, gin.H{
			"error": err.Error(),
		})
		return
	}

	c.JSON(201, nil)
}

func PredictionHandler(c *gin.Context) {
	// Get request params
	req := make(map[string]string)
	keys := []string{
		"user_id",
		"age_range",
		"gender",
		"merchant_id",
	}
	for _, key := range keys {
		req[key] = c.Query(key)
	}

	res, err := readers.PredictionReader(req)
	if err != nil && strings.Contains(err.Error(), "no prediction") {
		c.JSON(404, err.Error())
		return
	}

	if err != nil {
		c.JSON(500, err.Error())
		return
	}

	c.JSON(200, res)
}
