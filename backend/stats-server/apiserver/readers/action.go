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
	ActionFilePath = filepath.Join("action-output.txt")
)

func ActionReader() (*models.ActionResponse, error) {
	file, err := os.Open(ActionFilePath)
	if err != nil {
		log.Fatal(err)
	}
	defer func(file *os.File) {
		_ = file.Close()
	}(file)

	res := &models.ActionResponse{}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		split := strings.Split(scanner.Text(), "\t")
		if len(split) != 2 {
			continue
		}
		switch split[0] {
		case "0":
			cnt, err := strconv.Atoi(split[1])
			if err != nil {
				continue
			}
			res.Click = cnt
		case "1":
			cnt, err := strconv.Atoi(split[1])
			if err != nil {
				continue
			}
			res.Add = cnt
		case "2":
			cnt, err := strconv.Atoi(split[1])
			if err != nil {
				continue
			}
			res.Purchase = cnt
		case "3":
			cnt, err := strconv.Atoi(split[1])
			if err != nil {
				continue
			}
			res.Subscribe = cnt
		}
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return res, nil
}
