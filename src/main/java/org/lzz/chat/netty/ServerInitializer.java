package org.lzz.chat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> { //1

    @Autowired
    private TalkHandler talkHandler;

    @Override
    public void initChannel(SocketChannel ch) throws Exception {//2
         ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
        pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
        //pipeline.addLast("handler", new MyHandler());//指定房间
        pipeline.addLast("handler",talkHandler);//每两个匹配房间
    }

}