package com.cn.netty.simpleChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hebiao
 * @version $Id:ServerHandler.java, v0.1 2018/5/24 19:13 hebiao Exp $$
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.warn("收到消息：" + msg);
        Channel self = ctx.channel();
        for (Channel channel : channels) {
            if (!channel.equals(self)) {
                channel.writeAndFlush("client [" + self.remoteAddress().toString() + "] said :" + msg + "\n");
            } else {
                self.writeAndFlush("you said : " + msg + "\n");
            }
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();//上线
        channels.add(ctx.channel());
        channels.writeAndFlush("[SERVER]-" + incoming.remoteAddress() + "您已经加入聊天室！\n");
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush("[SERVER]-" + incoming.remoteAddress() + "离开\n");
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.warn("client [" + channel.remoteAddress().toString() + "]" + "上线\n");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.warn("client [" + channel.remoteAddress().toString() + "]" + "下线\n");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        try {
            super.exceptionCaught(ctx, cause);
        } catch (Exception e) {

        }
    }
}
