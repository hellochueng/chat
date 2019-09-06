package org.lzz.chat.mapreduce.scorecount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.lzz.chat.mapreduce.Conf;
import org.lzz.chat.mapreduce.Files;

import java.io.IOException;

public class UserScoreRun {
    //输入文件路径
    private static String inPath = "/score_output/part-r-00000";
    //输出文件目录
    private static String outPath = "/score_output1";

    public static void Run() {
        //创建score_input目录
        Files.mkdirFolder(outPath);

        //执行scorecount
        try {
            new UserScoreMR().MR();
            //成功后下载文件到本地
            Files.getFileFromHadoop("/score_output1/", "part-r-00000", "D:\\Hadoop\\download\\score1\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class UserScoreMR {
        public static int MR() throws Exception {
            //获取连接配置
            Configuration conf = Conf.get();
            //创建一个job实例
            Job job = Job.getInstance(conf, "UserScore");

            //设置job的主类
            job.setJarByClass(UserScoreMR.class);
            //设置job的mapper类和reducer类
            job.setMapperClass(UserScoreMapper.class);
            job.setReducerClass(UserScoreReducer.class);

            //设置Mapper的输出类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            //设置Reduce的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);
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

    static class UserScoreMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable k1, Text v1, Context context)
                throws IOException, InterruptedException {
            //将v1有Text转换为Java的String类型，方便处理
            String data = v1.toString();
            //分词：v1为“hello world”,按空格来划分为多个单词,即： hello 和 world
            String[] words = data.split(" ");
            StringBuilder res = new StringBuilder(words[1]);
            res.append(":");
            res.append(words[2]);
            //输出k2  , v2
            context.write(new Text(words[0].toString()+" "), new Text(res.toString()));
        }
    }

    static class UserScoreReducer extends Reducer<Text, Text, Text, NullWritable> {

        @Override
        protected void reduce(Text k3, Iterable<Text> v3,
                              Context context) throws IOException, InterruptedException {
            //定义一个变量来放求和值
            StringBuilder res = new StringBuilder(k3.toString());
            for (Text v : v3) {
                res.append(v.toString());
                res.append(",");
            }
            //输出 k4, v4
            context.write(new Text(res.toString()), NullWritable.get());
        }
    }
}