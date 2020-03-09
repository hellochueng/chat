package org.lzz.chat.mapreduce.wordcount;

import org.apache.log4j.Logger;
import org.lzz.chat.mapreduce.Files;

/**
 * 执行WordCount
 * Created by shirukai on 2017/11/8.
 */
public class WordCountRun {
    private static Logger logger = Logger.getLogger("this.class");

    public static void Run() {
        //创建word_input目录
        String folderName = "/word_input";
        Files.mkdirFolder(folderName);
        //创建word_input目录
        folderName = "/word_output";
        Files.mkdirFolder(folderName);
        //上传文件
        String localPath = "D:\\Hadoop\\upload\\";
        String fileName = "word.txt";
        String hdfsPath = "/word_input/";
        Files.uploadFile(localPath, fileName, hdfsPath);
        //执行wordcount

        try {
            new WordCountMR().MR();
            //成功后下载文件到本地
            String downPath = "/word_output/";
            String downName = "part-r-00000";
            String savePath = "D:\\Hadoop\\download\\";
            Files.getFileFromHadoop(downPath, downName, savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}