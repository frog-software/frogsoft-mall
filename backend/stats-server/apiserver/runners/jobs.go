package runners

import (
	"apiserver/readers"
	"path/filepath"
)

type JobConfig struct {
	JarPath    string
	MainClass  string
	OutputPath string
}

var GenderJob = JobConfig{
	JarPath:    filepath.Join("gender-0.0.1.jar"),
	MainClass:  "org.frogsoftware.gender.Gender",
	OutputPath: readers.GenderFilePath,
}

var AgeJob = JobConfig{
	JarPath:    filepath.Join("age-range-0.0.1.jar"),
	MainClass:  "AgeRangeAnalysis",
	OutputPath: readers.AgeFilePath,
}

var ProvinceJob = JobConfig{
	JarPath:    filepath.Join("province-0.0.1.jar"),
	MainClass:  "ProvinceAnalysis",
	OutputPath: readers.ProvinceFilePath,
}

var ActionJob = JobConfig{
	JarPath:    filepath.Join("action-0.0.1.jar"),
	MainClass:  "ActionsAnalysis",
	OutputPath: readers.ActionFilePath,
}

var CategoryJob = JobConfig{
	JarPath:    filepath.Join("category-0.0.1.jar"),
	MainClass:  "ProductSellAnalysis",
	OutputPath: readers.CategoryFilePath,
}
