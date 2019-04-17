package org.lzz.chat.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.lzz.chat.container.ChannelService;
import org.lzz.chat.container.TalkRoom;
import org.lzz.chat.container.TokenUser;
import org.lzz.chat.container.impl.ChannelServiceImpl;
import org.lzz.chat.container.impl.TalkRoomImpl;
import org.lzz.chat.container.impl.TokenUserImpl;
import org.lzz.chat.domain.Mes;
import org.lzz.chat.domain.User;
import org.lzz.chat.entity.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@ChannelHandler.Sharable
public class TalkHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Autowired
    private TokenUser tokenUser;

    @Autowired
    private TalkRoom talkRoom;

    @Autowired
    private ChannelService channelService;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof FullHttpRequest) { // 传统的HTTP接入
            handleHttpRequest(channelHandlerContext, (FullHttpRequest) o);
        } else if (o instanceof WebSocketFrame) { // WebSocket接入
            handleWebSocketFrame(channelHandlerContext, (WebSocketFrame) o);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 如果HTTP解码失败，返回HTTP异常
        if (!request.getDecoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        String[] param = request.getUri().split("/");

        //将用户信息加入
        String room = param[1];
        String token = param[2];
        String clientType = param[3];
        User user = tokenUser.getUserByToken(token);

        talkRoom.addRoom(user.getId(),room);

        // 正常WebSocket的Http连接请求，构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://" + request.headers().get(HttpHeaders.Names.HOST), null, false);

        handshaker = wsFactory.newHandshaker(request);

        if (handshaker == null) { // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());

        } else { // 向客户端发送websocket握手,完成握手
            handshaker.handshake(ctx.channel(), request);
        }

        //将连接通道加入集合
        channelService.addChannel(ctx, ClientType.valueOf(clientType),user.getId());
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 当前只支持文本消息，不支持二进制消息
        if ((frame instanceof TextWebSocketFrame)) {
            //获取发来的消息
            String text = ((TextWebSocketFrame) frame).text();

            //消息转成Mage
            Mes mes = Mes.strJson2Mage(text);


            User user = tokenUser.getUserByToken(mes.getToken());

            Set<String> roomUsers =  talkRoom.getRoomUser(mes.getRoom());

            roomUsers.forEach((id)->{

                ChannelHandlerContext app =  channelService.getChannelHandlerContext(ClientType.app,id);
                ChannelHandlerContext pc = channelService.getChannelHandlerContext(ClientType.pc,id);
                if("10001".equals(mes.getMessage())) {
                    if (app != null) app.writeAndFlush(new TextWebSocketFrame(user.getName() + "加入房间"));
                    if (pc != null) pc.writeAndFlush(new TextWebSocketFrame(user.getName() + "加入房间" ));
                } else {
                    if (app != null) app.writeAndFlush(new TextWebSocketFrame(user.getName() + ":" + mes.getMessage()));
                    if (pc != null) pc.writeAndFlush(new TextWebSocketFrame(user.getName() + ":" + mes.getMessage()));
                }
            });

////            if (!UserRoomContainer.getOnLine().containsKey(mes1.getId())) {
////
////                UserRoomContainer.getOnLine().put(mes1.getId(), mes1.getRoom());
////            }
////
////            ConcurrentMap<String, UserAndRoom> concurrentMap = null;
////
////            if (!UserRoomContainer.getMap().containsKey(mes1.getRoom())){
////
////                ConcurrentMap<String,UserAndRoom> map = new ConcurrentHashMap();
////
////                map.put(mes1.getId(),new UserAndRoom(ctx,mes1));
////
////                UserRoomContainer.getMap().put(mes1.getRoom(),map);
////
////                concurrentMap = map;
////            } else {
////                concurrentMap = UserRoomContainer.getMap().get(mes1.getRoom());
////                if(!concurrentMap.containsKey(mes1.getId())){
////                    concurrentMap.put(mes1.getId(),new UserAndRoom(ctx,mes1));
////                }
////            }
//
//            //将用户发送的消息发给所有在同一聊天室内的用户
//            System.out.println();
//
//            concurrentMap.forEach((id, iom) -> {
//                try {
//                    iom.send(mes1);
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//            });

        } else {
            System.err.println("------------------error--------------------------");
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        // 返回应答给客户端
        if (response.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(response, response.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(response);
        if (!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


}
