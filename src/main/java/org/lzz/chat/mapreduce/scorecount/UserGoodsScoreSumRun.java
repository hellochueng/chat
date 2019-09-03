package org.lzz.chat.mapreduce.scorecount;

import mapreduce.Conf;
import mapreduce.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGoodsScoreSumRun {
    //输入文件路径
    private static String inPath = "/score_output3/part-r-00000";
    //输出文件目录
    private static String outPath = "/score_output4";

    public static void Run() {
        //创建score_input目录
        Files.mkdirFolder(outPath);

        //执行scorecount
        try {
            new UserGoodsScoreSumMR().MR();
            //成功后下载文件到本地
            Files.getFileFromHadoop("/score_output4/", "part-r-00000", "D:\\Hadoop\\download\\score4\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class UserGoodsScoreSumMR {
        public static int MR() throws Exception {
            //获取连接配置
            Configuration conf = Conf.get();
            //创建一个job实例
            Job job = Job.getInstance(conf, "UserGoodsScoreSum");

            //设置job的主类
            job.setJarByClass(UserGoodsScoreSumMR.class);
            //设置job的mapper类和reducer类
            job.setMapperClass(UserGoodsScoreSumMapper.class);
            job.setReducerClass(UserGoodsScoreSumReducer.class);

            //设置Mapper的输出类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            //设置Reduce的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
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

    static class UserGoodsScoreSumMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
            String[] data = v1.toString().split("\t");
            context.write(new Text(data[0].toString()), new Text(data[1].toString()));
        }
    }

    static class UserGoodsScoreSumReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text k3, Iterable<Text> v3, Context context) throws IOException, InterruptedException {

            //定义一个变量来放求和值
            List<String> list = new ArrayList<>();
            for(Text v:v3) {
                list.add(v.toString());
            }
            Map<String,Float> res = new HashMap<>();
            for (int i=0;i<list.size();i++){
                String[] score = list.get(i).toString().split(",");
                if(!res.containsKey(score[0])){
                    Float total = Float.parseFloat(score[1]);
                    for(int j=i+1;j<list.size();j++){
                        if(score[0].equals(list.get(j).toString().split(",")[0])){
                            total+=Float.parseFloat(list.get(j).toString().split(",")[1]);
                        }
                    }
                    res.put(score[0],total);
                }
            }
            for(Map.Entry<String,Float> m:res.entrySet()){
                context.write(k3,new Text(m.getKey()+":"+String.valueOf(m.getValue())));
            }
        }
    }
}