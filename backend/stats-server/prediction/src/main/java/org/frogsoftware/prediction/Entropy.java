package org.frogsoftware.prediction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 计算信息熵
 */
public class Entropy {
    /**
     * 提交 job 计算各列的信息熵
     *
     * @param path 输入输出路径
     */
    public static void main(String[] path) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("mapreduce.output.textoutputformat.separator", "&");
        Job job = Job.getInstance(conf, "DataEntropy");

        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(path[0]));

        job.setJarByClass(Entropy.class);

        job.setMapperClass(EntropyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(EntropyReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(path[1]));

        job.waitForCompletion(true);
    }

    public static class EntropyMapper extends Mapper<Object, Text, Text, Text> {
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer sto = new StringTokenizer(value.toString());
            while (sto.hasMoreTokens()) {
                String[] row = sto.nextToken("\n").split("&");
                String keyString;
                String valueString;
                if (row.length == 4) {
                    // 四个分别为：列名 内容 标签 数量
                    keyString = row[0] + "&" + row[1]; // 列名&内容
                    valueString = row[2] + "&" + row[3]; // 标签&数量
                } else {
                    // 两个分别为：标签 数量
                    keyString = "system&";
                    valueString = row[0] + "&" + row[1]; // 标签&数量
                }
                context.write(new Text(keyString), new Text(valueString));
            }
        }
    }

    public static class EntropyReducer extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            // 统计各标签的情况
            int sum = 0;
            HashMap<String, Integer> count = new HashMap<>();
            for (Text value : values) {
                String[] tmp = value.toString().split("&");
                count.put(tmp[0], Integer.parseInt(tmp[1]));
                sum += Integer.parseInt(tmp[1]);
            }
            // 计算信息熵
            double entropy = 0;
            for (String label : count.keySet()) {
                double p = count.get(label) * 1.0 / sum;
                entropy += -p * Math.log(p) / Math.log(2);
            }
            // 输出 列名&内容 信息熵&数量&标签（第0个）
            context.write(key, new Text(entropy + "&" + sum + "&" + count.keySet().toArray()[0]));
        }
    }
}