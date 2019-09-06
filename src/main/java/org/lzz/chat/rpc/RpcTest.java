package org.lzz.chat.rpc;

public class RpcTest {

    public static void main(String[] args) {
        BatterCakeService batterCakeService=RpcConsumer.getService(BatterCakeService.class, "127.0.0.1", 20006);
        String result=batterCakeService.sellBatterCake("2");
        System.out.println(result);
    }
}