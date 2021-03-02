package org.lzz.chat.test;

//import com.spire.pdf.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExtractText {
//    public static void main(String[]args) throws Exception {
//        //加载测试文档
////        PdfDocument pdf = new PdfDocument("C:\\Users\\kingdee\\Desktop\\test.pdf");
//
//        //实例化StringBuilder类
//        StringBuilder sb = new StringBuilder();
//        //定义一个int型变量
//        int index = 0;
//
//        //遍历PDF文档中每页
////        PdfPageBase page;
//        for (int i= 0; i<pdf.getPages().getCount();i++) {
//            page = pdf.getPages().get(i);
//            //调用extractText()方法提取文本
//            sb.append(page.extractText(true));
//            FileWriter writer;
//            try {
//                //将StringBuilder对象中的文本写入到txt
//                writer = new FileWriter("ExtractText.txt");
//                writer.write(sb.toString());
//                writer.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            //调用extractImages方法获取图片
////            for (BufferedImage image : page.extractImages()) {
////                    //指定输出图片名，指定图片格式
////                    File output = new File(String.format("Image_%d.png", index++));
////                    ImageIO.write(image, "PNG", output);
////            }
//        }
//        pdf.close();
//    }

}