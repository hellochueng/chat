package org.lzz.chat.test;

import java.io.*;

public class test5 {

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\BaiduNetdiskDownload\\jd.txt");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] b = new byte[1024];
//        int read = fileInputStream.read(b);
//        System.out.println(read);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString = reader.readLine();

//        int line = 1;
//        while ((tempString = reader.readLine()) != null) {
//            line++;
//        }
//        System.out.println(line);
        System.out.println(tempString);
        reader.close();
    }
}
