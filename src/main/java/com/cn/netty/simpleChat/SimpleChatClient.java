/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.simpleChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author hebiao
 * @version $Id:SimpleChatClient.java, v0.1 2018/5/24 18:59 hebiao Exp $$
 */
public class SimpleChatClient {

  public static void main(String[] args) {
    new SimpleChatClient("localhost", 8090).run();
  }

  private final String host;
  private final int port;

  public SimpleChatClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void run() {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap()
          .group(group)
          .channel(NioSocketChannel.class)
          .handler(new SimpleChatClientInitializer());
      Channel channel = bootstrap.connect(host, port).sync().channel();
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      while (true) {
        channel.writeAndFlush(in.readLine() + "\r\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }

  }
}
