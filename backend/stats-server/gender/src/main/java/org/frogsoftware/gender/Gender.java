package org.frogsoftware.gender;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

enum ColumnName {
  user_id,
  item_id,
  cat_id,
  merchant_id,
  brand_id,
  month,
  day,
  action,
  age_range,
  gender,
  province
}

public class Gender {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.out.println("You needs 2 arguments standing for the input and output!");
      return;
    }

    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "actionsAnalysis");

    job.setJarByClass(Gender.class);

    job.setMapperClass(Gender.GenderMapper.class);
    job.setCombinerClass(Gender.GenderCombiner.class);
    job.setReducerClass(Gender.GenderReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);

    // 设置输入文件和输出文件的路径
    String inputFile = args[0];
    String outputFile = args[1];

    FileInputFormat.addInputPath(job, new Path(inputFile));
    FileOutputFormat.setOutputPath(job, new Path(outputFile));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }

  public static class GenderMapper extends Mapper<Object, Text, Text, Text> {

    private final Text keyInfo = new Text();
    private final Text valueInfo = new Text();

    public void map(Object key, Text value, Context context)
        throws IOException, InterruptedException {
      String line = value.toString();// 这一行的内容是什么
      String[] columns = line.split(",");
      // 0: female
      // 1: male
      // 2, NULL: unknown
      keyInfo.set(columns[ColumnName.gender.ordinal()]);
      valueInfo.set("1");
      context.write(keyInfo, valueInfo);
    }
  }

  public static class GenderCombiner extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {
      // 累加出现次数
      int count = 0;
      for (Text value : values) {
        count += Integer.parseInt(value.toString());
      }
      result.set(count + "");
      context.write(key, result);
    }
  }

  public static class GenderReducer extends Reducer<Text, Text, Text, Text> {

    private final Text result = new Text();

    public void reduce(Text key, Iterable<Text> values, Context context)
        throws IOException, InterruptedException {
      // 累加出现次数
      int count = 0;
      for (Text value : values) {
        count += Integer.parseInt(value.toString());
      }
      result.set(count + "");
      context.write(key, result);
    }
  }
}