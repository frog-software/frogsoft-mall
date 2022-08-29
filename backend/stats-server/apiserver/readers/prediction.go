package readers

import (
	"apiserver/models"
	"bufio"
	"fmt"
	"os"
	"path/filepath"
)

type Node struct {
	value    string
	children []*Node
	parent   *Node
	depth    int
}

var (
	PredictionFilePath = filepath.Join("prediction-output.txt")
)

func PredictionReader(req map[string]string) (*models.PredictionResponse, error) {
	root, err := readFile()
	if err != nil {
		return nil, err
	}

	res, err := findChildren(root, req)
	if err != nil {
		return nil, fmt.Errorf("no prediction")
	}

	return &models.PredictionResponse{
		Prediction: res.value == "1",
	}, nil
}

func findChildren(root *Node, req map[string]string) (*Node, error) {
	if len(root.children) == 1 && root.children[0].value == "0" || root.children[0].value == "1" {
		return root.children[0], nil
	}

	// For example {user_id: 123}
	// We will match user_id, then match 123
	// To reduce complexity
out:
	for _, child := range root.children {
		// find key
		for key, val := range req {
			if key == child.value {
				for _, subchild := range child.children {
					if val == subchild.value {
						return findChildren(subchild, req)
					}
				}
				break out
			}
		}
	}

	return nil, fmt.Errorf("not found")
}

func getLine(str string) (string, int) {
	var i int
	for i = 0; str[i] == ' '; i++ {
	}
	return str[i:], i
}
func NewNode(str string, depth int) *Node {
	return &Node{
		value:    str,
		children: []*Node{},
		depth:    depth,
	}
}

func readFile() (*Node, error) {
	// 打开文件
	file, err := os.Open(PredictionFilePath)
	if err != nil {
		return nil, err
	}
	defer func() {
		_ = file.Close()
	}()

	fileScanner := bufio.NewScanner(file)
	root := NewNode("root", -1)
	now := root
	for fileScanner.Scan() {
		// 获取内容和深度
		content, depth := getLine(fileScanner.Text())
		for now.depth+1 != depth {
			now = now.parent
		}
		// 创建新节点
		node := NewNode(content, depth)
		now.children = append(now.children, node)
		node.parent = now

		now = node
	}

	return root, nil
}
