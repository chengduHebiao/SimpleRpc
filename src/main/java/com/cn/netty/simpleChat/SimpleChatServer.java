/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.simpleChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的聊天服务器的服务端
 *
 * @author hebiao
 * @version $Id:SimpleChatServer.java, v0.1 2018/5/24 18:59 hebiao Exp $$
 */
public class SimpleChatServer {

  private Logger logger = LoggerFactory.getLogger(SimpleChatServer.class);
  private int port;

  public SimpleChatServer(int port) {
    this.port = port;
  }

  public void run() {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class) // (3)
          .childHandler(new SimpleChatServerInitializer())  //(4)
          .option(ChannelOption.SO_BACKLOG, 128)          // (5)
          .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
     logger.warn("聊天室启动，端口{}",port);
      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    int port;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    } else {
      port = 8090;
    }
    SimpleChatServer server = new SimpleChatServer(port);
    server.run();
  }


}
