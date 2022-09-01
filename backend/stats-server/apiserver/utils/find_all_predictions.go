package main

import (
	"bufio"
	"fmt"
	"os"
)

type Node struct {
	value    string
	children []*Node
	parent   *Node
	depth    int
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

func readFile(path string) (*Node, error) {
	// 打开文件
	file, err := os.Open(path)
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

func main() {
	args := os.Args
	if len(args) != 2 {
		fmt.Println("You need to provide a predication data file.")
		os.Exit(1)
	}
	filePath := args[1]

	root, err := readFile(filePath)
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}

	cnt := 0
	fmt.Println("user_id,merchant_id,prediction")

	for _, userIDKey := range root.children {
		for _, userIDVal := range userIDKey.children {
			for _, userIDorMerchantIDKey := range userIDVal.children {
				if userIDorMerchantIDKey.value == "0" ||
					userIDorMerchantIDKey.value == "1" {
					continue
				}
				for _, merchantIDVal := range userIDorMerchantIDKey.children {
					fmt.Printf("%s,%s,%s\n",
						userIDVal.value,
						merchantIDVal.value,
						merchantIDVal.children[0].value,
					)
					cnt++
				}
			}
		}
	}

	fmt.Printf("%d predictions total\n", cnt)
}
