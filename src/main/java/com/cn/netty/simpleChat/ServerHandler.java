/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.simpleChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author hebiao
 * @version $Id:ServerHandler.java, v0.1 2018/5/24 19:13 hebiao Exp $$
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

  public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  @Override
  public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    Channel self = ctx.channel();
    /*for (Channel channel : channels) {
      if (channel != self) {
        channel.writeAndFlush("client [" + self.remoteAddress().toString() + "] said :"  +msg);
      } else {
        channel.writeAndFlush("you said : " + msg);
      }
    }*/
    channels.writeAndFlush("[client said:] " + msg);

  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel incoming = ctx.channel();//上线
    channels.add(ctx.channel());
    channels.writeAndFlush("[SERVER]-" + incoming.remoteAddress() + "加入\n");
  }


  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    Channel incoming = ctx.channel();
    channels.writeAndFlush("[SERVER]-" + incoming.remoteAddress() + "离开\n");
  }


  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    System.out.println("client ["+channel.remoteAddress().toString()+"]"+"上线\n");
  }


  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    System.out.println("client ["+channel.remoteAddress().toString()+"]"+"下线\n");
  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }
}
