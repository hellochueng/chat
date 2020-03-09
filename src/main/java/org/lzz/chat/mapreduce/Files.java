package org.lzz.chat.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;

/**
 * 对HDFS文件操作
 */
public class Files {
    private static FileSystem fs = null;
    public synchronized static FileSystem getFiles() {
        if(fs==null) {
            //获得连接配置
            Configuration conf = Conf.get();
            try {
                fs = FileSystem.get(conf);
            } catch (IOException e) {
                System.out.println("配置连接失败" + e.getMessage());
            }
        }
        return fs;
    }

    public synchronized static void closeConnection() {
        if(fs!=null) {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 创建文件夹
     */
    public static Boolean exsit(String folderPath) {
        FileSystem fs = getFiles();
        try {
            System.out.println("创建文件夹成功："+folderPath);
            return fs.exists(new Path(folderPath));
        } catch (Exception e) {
            System.out.println("创建失败"+e.getMessage());
        }
        return false;
    }
    /**
     * 创建文件夹
     */
    public static void mkdirFolder(String folderPath) {
        FileSystem fs = getFiles();
        try {
            if(!exsit(folderPath))
                fs.mkdirs(new Path(folderPath));
            System.out.println("创建文件夹成功："+folderPath);
        } catch (Exception e) {
            System.out.println("创建失败"+e.getMessage());
        }
    }

    /**
     * 上传文件到hdfs
     *
     * @param localFolderPath 本地目录
     * @param fileName        文件名
     * @param hdfsFolderPath  上传到hdfs的目录
     */
    public static void uploadFile(String localFolderPath, String fileName, String hdfsFolderPath){
        FileSystem fs = getFiles();
        try {
            InputStream in = new FileInputStream(localFolderPath + fileName);
            OutputStream out = fs.create(new Path(hdfsFolderPath + fileName));
            IOUtils.copyBytes(in, out, 4096, true);
            System.out.println("上传文件成功："+fileName);
        } catch (Exception e) {
            System.out.println("上传文件失败"+e.getMessage());
        }
    }

    /**
     * 从hdfs获取文件
     *
     * @param downloadPath     hdfs的路径
     * @param downloadFileName hdfs文件名
     * @param savePath         保存的本地路径
     */
    public static void getFileFromHadoop(String downloadPath, String downloadFileName, String savePath) {
        FileSystem fs = getFiles();
        try {
            InputStream in = fs.open(new Path(downloadPath + downloadFileName));
            OutputStream out = new FileOutputStream(savePath + downloadFileName);
            IOUtils.copyBytes(in, out, 4096, true);
        }  catch (Exception e) {
            System.out.println("获取文件失败"+e.getMessage());
        }
    }

    /**
     * 删除文件
     * delete(path,boolean)
     * boolean如果为true，将进行递归删除，子目录及文件都会删除
     * false 只删除当前

     */
    public static void deleteFile(String deleteFilePath) {
        FileSystem fs = getFiles();
        //要删除的文件路径
        try {
            fs.delete(new Path(deleteFilePath), true);
        } catch (Exception e) {
            System.out.println("删除文件失败"+e.getMessage());
        }
    }

    /**
     * 日志打印文件内容
     * @param filePath 文件路径
     */
    public static void readOutFile(String filePath) {
        FileSystem fs = getFiles();
        try {
            InputStream inputStream = fs.open(new Path(filePath));
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "GB2312"));//防止中文乱码
            String line = null;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}