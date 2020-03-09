package org.lzz.chat.rpc;

public class RpcBootStrap {
    public static void main(String[] args) throws Exception {
        BatterCakeService batterCakeService =new BatterCakeServiceImpl();
        //发布卖煎饼的服务，注册在20006端口
        RpcProvider.export(20006,batterCakeService);
    }
}