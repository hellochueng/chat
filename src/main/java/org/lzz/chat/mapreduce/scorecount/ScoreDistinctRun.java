package org.lzz.chat.mapreduce.scorecount;

import mapreduce.Conf;
import mapreduce.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class ScoreDistinctRun {
    //输入文件路径
    private static String inPath = "/score_input/score.txt";
    //输出文件目录
    private static String outPath = "/score_output";

    public static void Run() {
        //创建score_input目录
        String folderName = "/score_input";
        Files.mkdirFolder(folderName);
        //上传文件
        Files.uploadFile("D:\\Hadoop\\upload\\", "score.txt", "/score_input/");
        //创建score_input目录
        Files.mkdirFolder(outPath);

        //执行scorecount
        try {
            new ScoreDistinctMR().MR();
            //成功后下载文件到本地
            Files.getFileFromHadoop("/score_output/", "part-r-00000", "D:\\Hadoop\\download\\score\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ScoreDistinctMR {
        public int MR() throws Exception{
            //获取连接配置
            Configuration conf = Conf.get();
            //创建一个job实例
            Job job = Job.getInstance(conf,"ScoreDistinct");

            //设置job的主类
            job.setJarByClass(ScoreDistinctMR.class);
            //设置job的mapper类和reducer类
            job.setMapperClass(ScoreDistinctMapper.class);
            job.setReducerClass(ScoreDistinctReducer.class);

            //设置Mapper的输出类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(FloatWritable.class);
            //设置Reduce的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(FloatWritable.class);
            //设置输入和输出路径
            FileSystem fs = Files.getFiles();
            Path inputPath = new Path(inPath);
            FileInputFormat.addInputPath(job,inputPath);
            Path outputPath = new Path(outPath);
            fs.delete(outputPath,true);

            FileOutputFormat.setOutputPath(job,outputPath);
            return job.waitForCompletion(true)?1:-1;
        }
    }

    static class ScoreDistinctMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
        @Override
        protected void map(LongWritable k1, Text v1, Context context)
                throws IOException, InterruptedException {
            //分词：v1为“hello world”,按空格来划分为多个单词,即： hello 和 world
            String[] words = v1.toString().split("\t");
            //输出k2  , v2
            context.write(new Text(words[0]+" "+words[1]), new FloatWritable(new Float(words[2])));
        }
    }

    static class ScoreDistinctReducer extends Reducer<Text, FloatWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text k3, Iterable<FloatWritable> v3,
                              Context context) throws IOException, InterruptedException {
            Float total=0F;
            for (FloatWritable f:v3){
                total+=f.get();
            }
            context.write(new Text(k3.toString()+" "+total), NullWritable.get());
        }
    }
}