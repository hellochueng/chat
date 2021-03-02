package org.lzz.chat.test;

import redis.clients.jedis.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public class test12 {
//    private static ShardedJedisPool pool;
//    static {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(100);
//        config.setMaxIdle(50);
//        config.setMaxWaitMillis(3000);
//        config.setTestOnBorrow(true);
//        config.setTestOnReturn(true);
//        // 集群
//        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("172.18.4.142", 6380);
//        jedisShardInfo1.setPassword("123456888888");
//        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
//        list.add(jedisShardInfo1);
//        pool = new ShardedJedisPool(config, list);
//    }

//    public static void main(String[] args) {
////        ShardedJedis jedis = pool.getResource();
////        String vaule = jedis.get("");
////        System.out.println(vaule);
////        Map<String,HashSet> map = null;
////        Optional.ofNullable(map).orElse(null).containsKey("123");
////        String a = null;
////        System.out.println("true".equals(a));
////        m.put("123","123");
////        m.put("123","1231");
//        InputStream in=null;
//        OutputStream os = null;
//        try {
//            in = new FileInputStream("C:\\Users\\kingdee\\Desktop\\reminder.txt");
//            byte[] b = new byte[1024];
//            int c= 0;
//            while ((c=in.read(b)) !=-1) {
//                os.write(b,0,c);
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//

    public static void main(String arg[]) {

        try {
            InputStreamReader read=null;
            String encoding = "utf-8"; // 字符编码(可解决中文乱码问题 )
            File file = new File("C:\\Users\\kingdee\\Desktop\\reminder.txt");
            read = new InputStreamReader(
                    new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTXT = null;
            while ((lineTXT = bufferedReader.readLine()) != null) {
                 System.out.println(lineTXT);
            }
            System.out.println(new BufferedReader(read).readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            System.out.println(new BufferedReader(read).readLine());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}
