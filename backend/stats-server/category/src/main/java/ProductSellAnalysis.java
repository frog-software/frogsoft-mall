import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

enum ColumnName{
    user_id,item_id,cat_id,merchant_id,brand_id,month,day,action,age_range,gender,province
}
/*
    Date:2022-06-14
    BY: WHT
    统计MR，用于统计各个类别商品的销售量
 */
public class ProductSellAnalysis {

    /** 定义Mapper
     * KEYIN: Object对象，代表原数据文本文件
     * VALUEIN: TEXT，原数据文件中的所有文本
     * KEYOUT: TEXT，商品类别的代号
     * VALUEOUT: IntWritable，一条记录中该商品是否有购买行为（1 ? 0）
     * */
    public static class ProductSellAnalysisMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final Text categoryKey = new Text();
        private final IntWritable buyActionValue = new IntWritable();

        public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
            String line= value.toString();
            // 分隔符','将一行的数据分割为列表
            String[] columns=line.split(",");

            // 获得商品类别id
            String cat_id = columns[ColumnName.cat_id.ordinal()];
            // 获得交易行为代号，其中2表示购买行为
            String action = columns[ColumnName.action.ordinal()];

            categoryKey.set(cat_id);
            if(action.equals("2")){
                buyActionValue.set(1);
            }
            else{
                buyActionValue.set(0);
            }
            context.write(categoryKey, buyActionValue);
        }
    }

    /** 定义Reducer
     * KEYIN: TEXT，传入的商品类别
     * VALUEIN: IntWritable，传入的购买信息
     * KEYOUT: TEXT，输出结果，商品类别的代号
     * VALUEOUT: TEXT，输出结果，该商品类别总销量，格式化为字符串
     */
    public static class ProductSellAnalysisReducer extends Reducer<Text, IntWritable, Text, Text> {

        private final Text result = new Text();

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

            int sellCount = 0;
            for(IntWritable value : values){
                sellCount += value.get();
            }
            result.set(String.format("%d", sellCount));
            context.write(key, result);
        }

    }

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("[ProductSellAnalysis]Param usage: <input> <output>");
            return;
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "productSellAnalysis");

        job.setJarByClass(ProductSellAnalysis.class);

        job.setMapperClass(ProductSellAnalysisMapper.class);
        job.setReducerClass(ProductSellAnalysisReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 设置输入文件和输出文件的路径
        String inputFile = args[0];
        String outputFile = args[1];

        FileInputFormat.addInputPath(job, new Path(inputFile));
        FileOutputFormat.setOutputPath(job, new Path(outputFile));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
