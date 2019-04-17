package org.lzz.chat.container;

import io.netty.channel.ChannelHandlerContext;
import org.lzz.chat.entity.ClientType;

public interface ChannelService {

    void addChannel(ChannelHandlerContext ctx, ClientType clientType, String id);

    void deleteChannel(ClientType clientType, String id);

    ChannelHandlerContext getChannelHandlerContext(ClientType clientType, String id);
}
