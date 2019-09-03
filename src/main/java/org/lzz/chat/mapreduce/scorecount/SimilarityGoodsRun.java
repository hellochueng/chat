package org.lzz.chat.mapreduce.scorecount;

import mapreduce.Conf;
import mapreduce.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class SimilarityGoodsRun {
    //输入文件路径
    private static String inPath = "/score_output1/part-r-00000";
    //输出文件目录
    private static String outPath = "/score_output2";

    public static void Run() {
        //创建score_input目录
        Files.mkdirFolder(outPath);

        //执行scorecount
        try {
            new UserScoreMR().MR();
            //成功后下载文件到本地
            Files.getFileFromHadoop("/score_output2/", "part-r-00000", "D:\\Hadoop\\download\\score2\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class UserScoreMR {
        public static int MR() throws Exception {
            //获取连接配置
            Configuration conf = Conf.get();
            //创建一个job实例
            Job job = Job.getInstance(conf, "SimilarityGoods");

            //设置job的主类
            job.setJarByClass(UserScoreMR.class);
            //设置job的mapper类和reducer类
            job.setMapperClass(SimilarityGoodsMapper.class);
            job.setReducerClass(SimilarityGoodsReducer.class);

            //设置Mapper的输出类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            //设置Reduce的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            //设置输入和输出路径
            FileSystem fs = Files.getFiles();
            Path inputPath = new Path(inPath);
            FileInputFormat.addInputPath(job, inputPath);
            Path outputPath = new Path(outPath);
            fs.delete(outputPath, true);

            FileOutputFormat.setOutputPath(job, outputPath);
            return job.waitForCompletion(true) ? 1 : -1;
        }
    }

    static class SimilarityGoodsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        protected void map(LongWritable k1, Text v1, Context context)
                throws IOException, InterruptedException {
            //将v1有Text转换为Java的String类型，方便处理
            String data = v1.toString();
            //分词：v1为“hello world”,按空格来划分为多个单词,即： hello 和 world
            String words = data.split(" ")[1];
            String[] content=words.split(",");
            for(int i=0;i<content.length;i++) {
                for (int j=i;j<content.length;j++) {
                    context.write(new Text(content[i].split(":")[0] + ":" + content[j].split(":")[0]), new IntWritable(1));
                }
            }
        }
    }

    static class SimilarityGoodsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        protected void reduce(Text k3, Iterable<IntWritable> v3,
                              Context context) throws IOException, InterruptedException {
            //定义一个变量来放求和值
            int total=0;
            for (IntWritable v : v3) {
                total+=v.get();
            }
            context.write(k3, new IntWritable(total));
        }
    }
}