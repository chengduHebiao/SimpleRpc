/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.simpleChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hebiao
 * @version $Id:SimpleChatClientHandler.java, v0.1 2018/5/24 19:44 hebiao Exp $$
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    System.out.println(s);
  }
}
