package org.lzz.chat.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable k1, Text v1, Context context)
			throws IOException, InterruptedException {
		//将v1有Text转换为Java的String类型，方便处理
		String data = v1.toString();
		//分词：v1为“hello world”,按空格来划分为多个单词,即： hello 和 world
		String[] words = data.split(" ");
		//输出k2  , v2
		for (String word : words) {
			context.write(new Text(word), new IntWritable(1));
		}
	}
}