package org.lzz.chat.test;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class hadoop {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        String HDFS_PATH = "hdfs://139.199.208.159:9000"; //要连接的hadoop
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", HDFS_PATH);

        //连接文件系统,FileSystem用来查看文件信息和创建文件
        FileSystem fileSystem = FileSystem.get(new URI(HDFS_PATH),configuration,"root");

        //操作文件io,用来读写
        configuration.set("fs.hdfs.impl", DistributedFileSystem.class.getName());  //设置处理分布式文件系统
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory(configuration));
        //FsUrlStreamHandlerFactory()  不加参数连接会无法识别的hdfs协议,原因是hadoop在获取处理hdfs协议的控制器时获取了configuration的fs.hdfs.impl值

        //获取Hadoop文件
        InputStream inputStream = new URL(HDFS_PATH + "/task1/words.txt").openStream();

        byte b[] = new byte[1024];
        int len = 0;
        int temp=0;          //所有读取的内容都使用temp接收
        while((temp=inputStream.read())!=-1){    //当没有读取完时，继续读取
            b[len]=(byte)temp;
            len++;
        }
        inputStream.close();
        System.out.println(new String(b,0,len));

    }
}
