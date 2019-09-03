package org.lzz.chat.mapreduce.wordcount;

import mapreduce.Conf;
import mapreduce.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * Created by shirukai on 2017/11/8.
 */
public class WordCountMR {
    //输入文件路径
    private static String inPath = "/word_input/word.txt";
    //输出文件目录
    private static String outPath = "/word_output1";
    public  int MR() throws Exception{
        //获取连接配置
        Configuration conf = Conf.get();
        //创建一个job实例
        Job job = Job.getInstance(conf,"wordCount");

        //设置job的主类
        job.setJarByClass(WordCountMR.class);
        //设置job的mapper类和reducer类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //设置Mapper的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置Reduce的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
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