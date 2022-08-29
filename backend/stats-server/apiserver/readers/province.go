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
	ProvinceFilePath = filepath.Join("province-output.txt")
)

func ProvinceReader() (*[]models.ProvinceResponse, error) {
	file, err := os.Open(ProvinceFilePath)
	if err != nil {
		log.Fatal(err)
	}
	defer func(file *os.File) {
		_ = file.Close()
	}(file)

	var res []models.ProvinceResponse

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		split := strings.Split(scanner.Text(), "\t")
		if len(split) != 2 {
			continue
		}

		obj := models.ProvinceResponse{}

		obj.Province = split[0]

		cnt, err := strconv.Atoi(split[1])
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
