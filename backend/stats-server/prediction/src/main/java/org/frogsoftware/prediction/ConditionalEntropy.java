package org.frogsoftware.prediction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 计算条件熵
 */
public class ConditionalEntropy {

    /**
     * 提交 job 计算条件熵
     *
     * @param path 输入输出路径
     */
    public static void main(String[] path) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("mapreduce.output.textoutputformat.separator", "&");
        Job job = Job.getInstance(conf, "GainEntropy");

        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(path[0]));

        job.setJarByClass(ConditionalEntropy.class);
        job.setMapperClass(ConditionalEntropyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);

        job.setReducerClass(ConditionalEntropyReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(path[1]));

        job.waitForCompletion(true);
    }

    public static class ConditionalEntropyMapper extends Mapper<Object, Text, Text, DoubleWritable> {
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer sto = new StringTokenizer(value.toString());
            while (sto.hasMoreTokens()) {
                String[] row = sto.nextToken("\n").split("&");
                if (!(row[0].equals("system"))) {
                    // 列名 内容 信息熵 数量
                    double tmp = Double.parseDouble(row[2]) * Double.parseDouble(row[3]);
                    // 输出 列名 信息熵*数量（计算条件熵所需）
                    context.write(new Text(row[0]), new DoubleWritable(tmp));
                }
            }
        }
    }

    public static class ConditionalEntropyReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        private final DoubleWritable result = new DoubleWritable();

        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
            double sum = 0;
            for (DoubleWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            // 输出 列名 条件熵
            context.write(key, result);
        }
    }
}