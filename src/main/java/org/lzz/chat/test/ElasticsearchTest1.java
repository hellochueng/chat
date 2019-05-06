package org.lzz.chat.test;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class ElasticsearchTest1 {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchTest1.class);

    public final static String HOST = "localhost";

    public final static int PORT = 9300;//http请求的端口是9200，客户端是9300

    /**
     * 测试Elasticsearch客户端连接
     * @Title: test1
     * @author sunt
     * @date 2017年11月22日
     * @return void
     * @throws UnknownHostException
     */
//    public void test1() throws UnknownHostException {
//     Map<String, String> map = new HashMap<String, String>();
//     map.put("cluster.name", "elasticsearch_wenbronk");
//     Settings.Builder settings = Settings.builder()
//     client = TransportClient.builder().settings(settings).build()
//             .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("www.wenbronk.com"), Integer.parseInt("9300")));
//    }
    public static Client getClient() throws UnknownHostException {
        String clusterName = "shopmall-es";
        List<String> clusterNodes = Arrays.asList("http://localhost:9300");
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        for (String node : clusterNodes) {
            URI host = URI.create(node);
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(host.getHost()), host.getPort()));
        }
        return client;
    }

    public static void main(String[] args) throws UnknownHostException {
        Client client = getClient();
        System.out.println(client.prepareIndex());
    }
}