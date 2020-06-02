package org.lzz.chat.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ipCompare {

    @Test
    public void a(){
        System.out.println("a");
        new Thread(()->{
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("第 " + i + " 次");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) throws IOException, JSONException {
//        File file2 = new File("C:\\python\\2.txt");
//        FileInputStream inputStream2 = new FileInputStream(file2);
//        Map<String,String> ipM = new HashMap();
//        buf = new byte[1024];
//        while((length = inputStream2.read(buf)) != -1){
//            String line = new String(buf,0,length);
//            String[] arr = line.split("\r\n");
//            for(String a:arr)
//                ipM.put(a.split(" ")[0],a.split(" ")[1]);
//        }
//        List<String> hkips = new ArrayList<>();
//        for (String ip : ips){
//            for(Map.Entry<String,String> entry:ipM.entrySet()){
//                String[] start = entry.getKey().split("\\.");
//                String[] end = entry.getValue().split("\\.");
//                String[] compare = ip.split("\\.");
//                if(start[0].equals(compare[0])&&start[1].equals(compare[1])){
//                    if(Integer.parseInt(start[2])<=Integer.parseInt(compare[2])
//                            &&Integer.parseInt(end[2])>=Integer.parseInt(compare[2])
//                            &&Integer.parseInt(start[3])<=Integer.parseInt(compare[3])
//                            &&Integer.parseInt(end[3])>=Integer.parseInt(compare[3]))
//                    hkips.add(ip);
//                }
//            }
//        }
//        hkips.forEach(s->{
//            System.out.println(s);
//        });
    }

    public static Object Get(Map<String, Object> parameter,String URL) throws IOException {
        String result;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个post对象

        //创建一个Entity。模拟一个表单
        StringBuilder params = null;
        if (parameter.size() > 0) {
            params = new StringBuilder("?");
            for (Map.Entry<String, Object> entry : parameter.entrySet())
                params.append(entry.getKey() + "=" + entry.getValue()+"&");
            params.deleteCharAt(params.length() - 1);
        }
        HttpGet get = new HttpGet(URL.toString() + params.toString());
        //执行post请求

        CloseableHttpResponse response = httpClient.execute(get);
        result = EntityUtils.toString(response.getEntity());
        response.close();
        httpClient.close();
        return result;
    }
}
