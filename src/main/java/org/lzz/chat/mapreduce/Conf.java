package org.lzz.chat.mapreduce;

import org.apache.hadoop.conf.Configuration;

/**
 * 获得hadoop连接配置
 */
public class Conf {
    public static Configuration get (){
        //hdfs的链接地址
        String hdfsUrl = "hdfs://192.168.1.197:9000";
        //hdfs的名字
        String hdfsName = "fs.defaultFS";
        //jar包文位置(上一个步骤获得的jar路径)
        String jarPath = "C:\\project\\hadooptest1\\target\\hadooptest1.jar";
        Configuration conf = new Configuration();
        conf.set(hdfsName,hdfsUrl);

        conf.set("mapreduce.app-submission.cross-platform", "true");
        conf.set("mapreduce.job.jar",jarPath);
        return conf;
    }
}