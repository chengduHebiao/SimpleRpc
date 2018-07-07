/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.simpleChat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author hebiao
 * @version $Id:SimpleChatClientInitializer.java, v0.1 2018/5/24 19:43 hebiao Exp $$
 */
public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  public void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();

    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
    pipeline.addLast("decoder", new StringDecoder());
    pipeline.addLast("encoder", new StringEncoder());
    pipeline.addLast("handler", new SimpleChatClientHandler());
  }
}
