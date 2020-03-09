package org.lzz.chat.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void reduce(Text k3, Iterable<IntWritable> v3,
                          Context context) throws IOException, InterruptedException {
		//定义K4,并赋值为k3
		Text k4 = k3;
		//定义一个变量来放求和值
		int total=0;
		for (IntWritable v : v3) {
			total = total + v.get();//get()方法是将IntWritable数据变为Int类型
		}
		//输出 k4, v4
		context.write(k4, new IntWritable(total));
	}
}