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
	AgeFilePath = filepath.Join("age-range-output.txt")
)

func AgeReader() (*[]models.AgeResponse, error) {
	file, err := os.Open(AgeFilePath)
	if err != nil {
		log.Fatal(err)
	}
	defer func(file *os.File) {
		_ = file.Close()
	}(file)

	var res []models.AgeResponse

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		noSpace := strings.ReplaceAll(scanner.Text(), " ", "\t")
		split := strings.Split(noSpace, "\t")
		if len(split) != 3 {
			continue
		}

		obj := models.AgeResponse{}

		gender, err := strconv.Atoi(split[0])
		if err != nil {
			return nil, err
		}
		obj.Gender = gender

		age, err := strconv.Atoi(split[1])
		if err != nil {
			return nil, err
		}
		obj.AgeRange = age

		cnt, err := strconv.Atoi(split[2])
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
