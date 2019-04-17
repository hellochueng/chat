package org.lzz.chat.container.impl;

import io.netty.channel.ChannelHandlerContext;
import org.lzz.chat.container.ChannelService;
import org.lzz.chat.entity.ClientType;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChannelServiceImpl implements ChannelService {

    public Map<String,ChannelHandlerContext> channelHandlerContextMap = new ConcurrentHashMap<>();

    @Override
    public void addChannel(ChannelHandlerContext ctx, ClientType clientType, String id) {

        if(channelHandlerContextMap.containsKey(clientType.getInfo()+id)) {

            channelHandlerContextMap.remove(clientType.getInfo()+id);
        }

        channelHandlerContextMap.put(clientType.getInfo() + id, ctx);
    }

    @Override
    public void deleteChannel(ClientType clientType, String id) {

        channelHandlerContextMap.remove(clientType.getInfo()+id);
    }

    @Override
    public ChannelHandlerContext getChannelHandlerContext(ClientType clientType, String id) {

        return channelHandlerContextMap.get(clientType.getInfo() + id);
    }

}
