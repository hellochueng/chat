package org.lzz.chat;

import org.lzz.chat.netty.WebsocketChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NettyStart implements CommandLineRunner {

    @Autowired
    private WebsocketChatServer websocketChatServer;

    @Override
    public void run(String... args) throws Exception {
        websocketChatServer.run();
    }
}
