package org.frogsoftware.prediction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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
 * 统计数据
 */
public class CountData {

    /**
     * 提交 job 统计出现次数
     *
     * @param path   输入输出路径
     * @param filter 过滤条件
     */
    public static void main(String[] path, String[] filter) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("filter", String.join(",", filter));
        conf.set("mapreduce.output.textoutputformat.separator", "&");
        Job job = Job.getInstance(conf, "CountData");

        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(path[0]));

        job.setJarByClass(CountData.class);

        job.setMapperClass(CountDataMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(CountDataReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(path[1]));

        job.waitForCompletion(true);
    }

    public static class CountDataMapper extends Mapper<Object, Text, Text, IntWritable> {
        public String[] filter;

        public void setup(Context context) {
            //从全局配置获取配置参数
            Configuration conf = context.getConfiguration();
            filter = conf.get("filter").split(",", -1);
        }

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            IntWritable one = new IntWritable(1);
            StringTokenizer sto = new StringTokenizer(value.toString());


            while (sto.hasMoreTokens()) {
                String[] row = sto.nextToken("\n").split(",");

                // 查看是否符合 filter 的要求
                boolean canUse = true;
                for (int i = 0; i < filter.length; i++) {
                    if (filter[i] != null && filter[i].length() > 0) {
                        canUse = canUse && row[i].equals(filter[i]);
                    }
                }
                if (!canUse) continue;

                String label = row[row.length - 1];
                ColumnName[] title = ColumnName.values();
                context.write(new Text(label), one); // 增加标签
                for (int i = 0; i < row.length - 1; i++) {
                    if (!row[i].equals(filter[i])) {
                        context.write(new Text(title[i] + "&" + row[i] + "&" + label), one); // 增加标签
                    }
                }
            }
        }
    }

    public static class CountDataReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private final IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }
}