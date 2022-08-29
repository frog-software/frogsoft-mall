package readers

import (
	"apiserver/models"
	"bufio"
	"log"
	"os"
	"path/filepath"
	"strconv"
	"strings"
)

var (
	CategoryFilePath = filepath.Join("category-output.txt")
)

func CategoryReader() (*[]models.CategoryResponse, error) {
	file, err := os.Open(CategoryFilePath)
	if err != nil {
		log.Fatal(err)
	}
	defer func(file *os.File) {
		_ = file.Close()
	}(file)

	var res []models.CategoryResponse

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		split := strings.Split(scanner.Text(), "\t")
		if len(split) != 2 {
			continue
		}

		obj := models.CategoryResponse{}

		cnt, err := strconv.Atoi(split[0])
		if err != nil {
			return nil, err
		}
		obj.Category = cnt

		cnt, err = strconv.Atoi(split[1])
		if err != nil {
			return nil, err
		}
		obj.Count = cnt

		res = append(res, obj)
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return &res, nil
}
