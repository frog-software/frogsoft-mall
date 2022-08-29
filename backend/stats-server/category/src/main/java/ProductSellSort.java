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


/*
    Date:2022-06-14
    BY: WHT
    排序MR，用于对已统计数据进行排序
 */
public class ProductSellSort {

    /** 定义Mapper
     * KEYIN: Object对象，代表原数据文本文件
     * VALUEIN: TEXT，原数据文件中的所有文本
     * KEYOUT: IntWritable，商品销售量（已统计）
     * VALUEOUT: TEXT，商品类别的代号
     * */
    public static class ProductSellSortMapper extends Mapper<Object, Text, IntWritable, Text> {

        // key和value互换，以便MapReduce根据销售量的key进行排序
        private final IntWritable outKey = new IntWritable();
        private final Text outValue = new Text();

        public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

            String line = value.toString();
            // 输入行格式： 【商品列表】 【统计销售总数】 分隔符是“\t"
            String[] columns = line.split("\t");

            outKey.set(Integer.parseInt(columns[1]));
            outValue.set(columns[0]);

            context.write(outKey,outValue);
        }
    }
    
    /** 定义Reducer
     * KEYIN: IntWritable，传入的销售数值
     * VALUEIN: TEXT，传入的商品类别
     * KEYOUT: TEXT，输出结果，商品类别的代号
     * VALUEOUT: TEXT，输出结果，该商品类别总销量，格式化为字符串
     */
    public static class ProductSellSortReducer extends Reducer<IntWritable, Text, Text, Text> {

        private final Text result = new Text(); // result仍然表示统计总销售数，int转str

        public void reduce(IntWritable key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

            // 同样的key表明，存在销售数相同的商品类别，将每个对应的value都输出，该情况下排名不分先后
            for(Text catValue : values){
                result.set(String.format("%d", key.get()));
                // 排序后反向输出Text，保持格式一致
                context.write(catValue, result);
            }

        }

    }


    /*
       重写一个降序排列的比较器，以便Reduce阶段能使用降序排序
     */
    public static class IntWritableDecreasingComparator extends IntWritable.Comparator {

        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            return -super.compare(b1, s1, l1, b2, s2, l2);
        }
    }


    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("[ProductSellSort]Param usage: <input> <output>");
            return;
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "productSellSort");

        job.setJarByClass(ProductSellSort.class);

        job.setMapperClass(ProductSellSortMapper.class);
        job.setReducerClass(ProductSellSortReducer.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        // 设置排序比较器类
        job.setSortComparatorClass(IntWritableDecreasingComparator.class);

        // 设置输入文件和输出文件的路径
        String inputFile = args[0];
        String outputFile = args[1];

        FileInputFormat.addInputPath(job, new Path(inputFile));
        FileOutputFormat.setOutputPath(job, new Path(outputFile));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
