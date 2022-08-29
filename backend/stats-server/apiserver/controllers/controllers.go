package controllers

import (
	"apiserver/handlers"
	"apiserver/routes"
	"github.com/gin-gonic/gin"
)

type Controller struct {
	Method  string
	Path    string
	Handler func(c *gin.Context)
}

func Controllers() []Controller {
	return []Controller{
		{
			Method:  "GET",
			Path:    routes.Gender,
			Handler: handlers.GenderHandler,
		},
		{
			Method:  "POST",
			Path:    routes.GenderRun,
			Handler: handlers.GenderRunHandler,
		},
		{
			Method:  "GET",
			Path:    routes.Age,
			Handler: handlers.AgeHandler,
		},
		{
			Method:  "POST",
			Path:    routes.AgeRun,
			Handler: handlers.AgeRunHandler,
		},
		{
			Method:  "GET",
			Path:    routes.Province,
			Handler: handlers.ProvinceHandler,
		},
		{
			Method:  "POST",
			Path:    routes.ProvinceRun,
			Handler: handlers.ProvinceRunHandler,
		},
		{
			Method:  "GET",
			Path:    routes.Action,
			Handler: handlers.ActionHandler,
		},
		{
			Method:  "POST",
			Path:    routes.ActionRun,
			Handler: handlers.ActionRunHandler,
		},
		{
			Method:  "GET",
			Path:    routes.Category,
			Handler: handlers.CategoryHandler,
		},
		{
			Method:  "POST",
			Path:    routes.CategoryRun,
			Handler: handlers.CategoryRunHandler,
		},
		{
			Method:  "GET",
			Path:    routes.Prediction,
			Handler: handlers.PredictionHandler,
		},
	}
}
