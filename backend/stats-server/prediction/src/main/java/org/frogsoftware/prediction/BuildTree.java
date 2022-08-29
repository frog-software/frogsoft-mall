package org.frogsoftware.prediction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class BuildTree {
    private static String inputFilePath;
    private static String outputFilePath;

    public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
        if (args.length != 2) {
            System.out.println("You needs 2 arguments standing for the input and output!");
            return;
        }
        inputFilePath = args[0];
        outputFilePath = args[1];

        if (outputFilePath.charAt(outputFilePath.length() - 1) != '/') {
            outputFilePath += "/";
        }

        String[] filter = new String[ColumnName.values().length - 1];
        Arrays.fill(filter, "");
        StringBuilder sb = new StringBuilder();
        makeTree("system", null, filter).Traversal(0, sb);

        FSDataOutputStream out = FileSystem.get(new Configuration()).create(new Path(outputFilePath + "output.txt"));
        out.write(sb.toString().getBytes());
        out.close();
        System.out.println(sb);
        System.out.println("BuildTree finished!");

    }

    // get file content
    public static String[] getFileContent(String remoteFilePath) throws IOException {
        FileSystem fs = FileSystem.get(new Configuration());
        Path remotePath = new Path(remoteFilePath);
        FSDataInputStream in = fs.open(remotePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        in.close();
        fs.close();
        return lines.toArray(new String[]{});
    }

    /**
     * @param name   当前属性名
     * @param parent 树的父节点
     * @param filter 过滤条件
     * @return 树的根节点
     */
    public static TreeNode makeTree(String name, TreeNode parent, String[] filter) throws ClassNotFoundException, IOException, InterruptedException {
        String[] path = {inputFilePath + "/*", outputFilePath + name + "_output"};
        CountData.main(path, filter);
        String[] path1 = {path[1] + "/*", outputFilePath + name + "_output1"};
        Entropy.main(path1);
        String[] path2 = {path1[1] + "/*", outputFilePath + name + "_output2"};
        ConditionalEntropy.main(path2);

        // 信息增益＝系统信息熵－条件熵
        // 所以条件熵最小表示信息增益最大
        // 找信息增益最大的列名
        String columnName = "";
        double min = Double.MAX_VALUE;
        for (String line : getFileContent(path2[1] + "/part-r-00000")) {
            String[] row = line.split("&");
            if (min > Double.parseDouble(row[1])) {
                min = Double.parseDouble(row[1]);
                columnName = row[0];
            }
        }

        // 新增一个节点
        TreeNode nodeColumn = new TreeNode(columnName);
        if (parent == null) {
            parent = nodeColumn;
        } else {
            parent.addChild(nodeColumn);
        }

        // 获取该列名的所有值对应的信息熵
        HashMap<String, Object[]> entropyMap = new HashMap<>();
        for (String detail : getFileContent(path1[1] + "/part-r-00000")) {
            // 列名&内容 信息熵&数量&标签（第0个）
            String[] row = detail.split("&");
            if (row[0].equals(columnName)) {
                entropyMap.put(row[1], new Object[]{Double.parseDouble(row[2]), row[4]});
            }
        }

        // 对于该列的每个值，构建子树
        for (String item : entropyMap.keySet()) {
            TreeNode nodeItem = new TreeNode(item);
            nodeColumn.addChild(nodeItem);
            double entropy = (double) entropyMap.get(item)[0];
            if (entropy == 0) {
                nodeItem.addChild(new TreeNode((String) entropyMap.get(item)[1]));
            } else {
                String[] newFilter = filter.clone();
                newFilter[ColumnName.valueOf(columnName).ordinal()] = item;
                makeTree(name + "_" + columnName + "_" + item, nodeItem, newFilter);
            }
        }
        return parent;
    }

    /**
     * 树节点
     */
    public static class TreeNode {
        private final String value; //数据区
        private final List<TreeNode> childrenList; //孩子节点指针集合

        public TreeNode(String value) {
            this.value = value;
            this.childrenList = new ArrayList<>();
        }

        /**
         * 遍历树
         *
         * @param depth 树的深度
         */
        public void Traversal(int depth, StringBuilder sb) {
            for (int i = 0; i < depth; i++) {
                sb.append(" ");
            }
            sb.append(value).append("\n");
            for (TreeNode treeNode : this.childrenList) {
                treeNode.Traversal(depth + 1, sb);
            }
        }

        /**
         * 添加孩子节点
         *
         * @param node 孩子节点
         */
        public void addChild(TreeNode node) {
            this.childrenList.add(node);
        }
    }
}

