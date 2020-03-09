package org.lzz.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("video")
public class VideoController {

    @GetMapping("getVideo")
    public void getVideo(HttpServletResponse response){
        byte[] bytes = file2byte("d:\\1.mp4");

        response.setContentType("application/octet-stream");
        response.setContentLength(bytes.length);
        try {
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            System.out.println("IO异常----");
        }
    }

    public byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
