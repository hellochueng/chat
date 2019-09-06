package org.lzz.chat.mapreduce.scorecount;

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
import org.lzz.chat.mapreduce.Conf;
import org.lzz.chat.mapreduce.Files;
import org.lzz.chat.mapreduce.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserGoodsRun {
    //输入文件路径
    private static String inPath = "/score_output2/part-r-00000";
    private static String secondInPath = "/score_output1/part-r-00000";
    //输出文件目录
    private static String outPath = "/score_output3";

    public static void Run() {
        //创建score_input目录
        Files.mkdirFolder(outPath);

        //执行scorecount
        try {
            new UserGoodsMR().MR();
            //成功后下载文件到本地
            Files.getFileFromHadoop("/score_output3/", "part-r-00000", "D:\\Hadoop\\download\\score3\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class UserGoodsMR {
        public static int MR() throws Exception {
            //获取连接配置
            Configuration conf = Conf.get();
            //创建一个job实例
            Job job = Job.getInstance(conf, "UserGoods");

            //设置job的主类
            job.setJarByClass(UserGoodsMR.class);
            //设置job的mapper类和reducer类
            job.setMapperClass(UserGoodsMapper.class);
            job.setReducerClass(UserGoodsReducer.class);

            //设置Mapper的输出类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            //设置Reduce的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            //设置输入和输出路径
            FileSystem fs = Files.getFiles();
            FileInputFormat.addInputPath(job, new Path(inPath));
            FileInputFormat.addInputPath(job,new Path(secondInPath));
            Path outputPath = new Path(outPath);
            fs.delete(outputPath, true);

            FileOutputFormat.setOutputPath(job, outputPath);
            return job.waitForCompletion(true) ? 1 : -1;
        }
    }

    static class UserGoodsMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
            //判断是哪种类型
            if(!Util.isEmpty(v1.toString())) {
                String[] value = v1.toString().split(" ");
                if (value.length==1) {
                    value=value[0].split("\t");
                    context.write(new Text(value[0].split(":")[0]), new Text("A:" + value[0].split(":")[1] + "," + value[1]));
                } else {
                    for (String score : value[1].split(",")) {
                        context.write(new Text(score.split(":")[0]), new Text("B:" + value[0] + "," + score.split(":")[1]));
                    }
                }
            }
        }
    }

    static class UserGoodsReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text k3, Iterable<Text> v3, Context context) throws IOException, InterruptedException {
            Map<String,String> mapA = new HashMap();
            Map<String,String> mapB = new HashMap();

            for(Text v : v3){
                String[] score = v.toString().split(":");
                if (score[0].equals("A")) {
                    mapA.put(score[1].split(",")[0], score[1].split(",")[1]);
                } else if (score[0].equals("B")) {
                    mapB.put(score[1].split(",")[0], score[1].split(",")[1]);
                }
            }
            for(Map.Entry<String,String> entryA:mapA.entrySet()){
                Float total=0F;
                for(Map.Entry<String,String> entryB:mapB.entrySet()){
                    total+=new Float(entryA.getValue())*new Float(entryB.getValue());
                    context.write(new Text(entryB.getKey()), new Text(entryA.getKey()+","+String.valueOf(total)));
                }
            }
        }
    }
}