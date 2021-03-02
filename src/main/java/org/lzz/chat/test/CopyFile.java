package org.lzz.chat.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
public class CopyFile {
public static void main(String[] args) {
    Configuration conf = new Configuration();
    String localDir = "C:\\Users\\kingdee\\Desktop\\start.bat";
    String hdfsDir = "hdfs://localhost:9000/test";
    try{
            Path localPath = new Path(localDir);
            Path hdfsPath = new Path(hdfsDir);
            FileSystem hdfs = FileSystem.get(conf);
            if(!hdfs.exists(hdfsPath)){
                 hdfs.mkdirs(hdfsPath);
             }
             hdfs.copyFromLocalFile(localPath, hdfsPath);
         }catch(Exception e){
         e.printStackTrace();
         }
    }
}