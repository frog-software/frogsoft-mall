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
	GenderFilePath = filepath.Join("gender-output.txt")
)

func GenderReader() (*models.GenderResponse, error) {
	file, err := os.Open(GenderFilePath)
	if err != nil {
		log.Fatal(err)
	}
	defer func(file *os.File) {
		_ = file.Close()
	}(file)

	res := &models.GenderResponse{}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		split := strings.Split(scanner.Text(), "\t")
		if len(split) != 2 {
			continue
		}
		switch split[0] {
		case "0":
			femaleCnt, err := strconv.Atoi(split[1])
			if err != nil {
				continue
			}
			res.Female = femaleCnt
		case "1":
			maleCnt, err := strconv.Atoi(split[1])
			if err != nil {
				continue
			}
			res.Male = maleCnt
		case "2":
			unknownCnt, err := strconv.Atoi(split[1])
			if err != nil {
				continue
			}
			res.Unknown = unknownCnt
		}
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return res, nil
}
