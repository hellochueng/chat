package org.lzz.chat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.lzz.chat.domain.Mes;

public class UserAndRoom {

    private ChannelHandlerContext ctx;

    private Mes mes;

    public void send(Mes mes) throws Exception {

        this.ctx.writeAndFlush(new TextWebSocketFrame(mes.toJson()));
    }

    public UserAndRoom(ChannelHandlerContext ctx, Mes mes) {
        this.ctx = ctx;
        this.mes = mes;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }
}
