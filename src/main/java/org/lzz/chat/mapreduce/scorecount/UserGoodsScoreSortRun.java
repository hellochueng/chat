package org.lzz.chat.mapreduce.scorecount;

import mapreduce.Conf;
import mapreduce.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UserGoodsScoreSortRun {
    //输入文件路径
    private static String inPath = "/score_output4/part-r-00000";
    //输出文件目录
    private static String outPath = "/score_output5";

    public static void Run() {
        //创建score_input目录
        Files.mkdirFolder(outPath);

        //执行scorecount
        try {
            new UserGoodsScoreSortMR().MR();
            //成功后下载文件到本地
            Files.getFileFromHadoop("/score_output5/", "part-r-00000", "D:\\Hadoop\\download\\score5\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class UserGoodsScoreSortMR {
        public static int MR() throws Exception {
            //获取连接配置
            Configuration conf = Conf.get();
            //创建一个job实例
            Job job = Job.getInstance(conf, "UserGoodsScoreSort");

            //设置job的主类
            job.setJarByClass(UserGoodsScoreSortMR.class);
            //设置job的mapper类和reducer类
            job.setMapperClass(UserGoodsScoreSortMapper.class);
            job.setReducerClass(UserGoodsScoreSortReducer.class);

            //设置Mapper的输出类型
            job.setMapOutputKeyClass(PairWritable.class);
            job.setMapOutputValueClass(Text.class);
            //设置Reduce的输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            //设置分区类
            job.setPartitionerClass(SecondPartitioner.class);
            job.setNumReduceTasks(1);
            //5排序分组
            job.setGroupingComparatorClass(SecondGroupComparator.class);
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

    static class UserGoodsScoreSortMapper extends Mapper<LongWritable, Text, PairWritable, Text> {
        private PairWritable mapOutKey = new PairWritable();
        @Override
        protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
            String[] strs = v1.toString().split("\t");
            mapOutKey.set(strs[0], strs[1]);
            context.write(mapOutKey, new Text(strs[1]));
        }
    }

    static class UserGoodsScoreSortReducer extends Reducer<PairWritable, Text, Text, Text> {
        @Override
        protected void reduce(PairWritable k3, Iterable<Text> v3, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            int total=0;
            for (Text v:v3){
                total++;
                if(total>=100) break;
                sb.append(v.toString());
                sb.append(",");
            }
            context.write(new Text(k3.getFirst()),new Text(sb.toString()));
        }
    }

    static class SecondPartitioner extends Partitioner<PairWritable, Text> {

        @Override
        public int getPartition(PairWritable key, Text value, int numPartitions) {
            /*
             * 让key中first字段作为分区依据
             */
            return (key.getFirst().hashCode() & Integer.MAX_VALUE) % numPartitions;
        }
    }
    static class SecondGroupComparator implements RawComparator<PairWritable> {

        public int compare(PairWritable o1, PairWritable o2) {
            return o1.getFirst().compareTo(o2.getFirst());
        }
        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            return WritableComparator.compareBytes(b1, s1, Integer.SIZE/8, b2, s2, Integer.SIZE/8);
        }
    }

    static class PairWritable implements WritableComparable<PairWritable> {
        // 组合key
        private String first;
        private String second;

        //重写比较器
        public int compareTo(PairWritable o) {
            int comp = this.first.compareTo(o.first);
            if (comp != 0) {
                return comp;
            } else { // 若第一个字段相等，则比较第二个字段
                return Float.valueOf(o.getSecond().split(":")[1]).compareTo(Float.valueOf(this.second.split(":")[1]));
            }
        }

        public String getSecond() { return second; }
        public void setSecond(String second) { this.second = second; }
        public String getFirst() { return first; }
        public void setFirst(String first) { this.first = first; }
        public PairWritable() {}
        public PairWritable(String first, String second) { this.set(first, second); }
        //方便设置字段
        public void set(String first, String second) { this.first = first; this.second = second; }
        //反序列化
        @Override
        public void readFields(DataInput arg0) throws IOException { this.first = arg0.readUTF(); this.second = arg0.readUTF(); }
        //序列化
        @Override
        public void write(DataOutput arg0) throws IOException { arg0.writeUTF(first); arg0.writeUTF(second); }

    }
}