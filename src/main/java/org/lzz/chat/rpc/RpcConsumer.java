package org.lzz.chat.rpc;

import java.lang.reflect.Proxy;

public class RpcConsumer {
    
    public static <T> T getService(Class<T> clazz,String ip,int port) {
        ProxyHandler proxyHandler =new ProxyHandler(ip,port);
        return (T)Proxy.newProxyInstance(RpcConsumer.class.getClassLoader(), new Class<?>[] {clazz}, proxyHandler);
    }
}