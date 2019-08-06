/**
 * Inc All Rights Reserved @2018
 */

package com.cn.rpc.handler;

import com.cn.rpc.domain.RpcRequest;
import com.cn.rpc.domain.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hebiao
 * @version $Id:RpcRequestSender.java, v0.1 2018/6/1 11:46 hebiao Exp $$
 */
public class RpcRequestSender extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger log = LoggerFactory.getLogger(RpcRequestSender.class);

    private BlockingQueue<RpcResponse> responseHolder = new LinkedBlockingQueue<RpcResponse>(1);


    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        responseHolder.put(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public RpcResponse send(RpcRequest request, String host, int port) throws Exception {
        if (port < 0) {
            port = 8099;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new RpcEncode(RpcRequest.class))
                                    .addLast(new RpcDecode(RpcResponse.class))
                                    .addLast(RpcRequestSender.this);
                        }

                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            Channel channel = future.channel();

            channel.writeAndFlush(request).sync();
            /**
             *
             * 使用闭锁实现等待
             */
            RpcResponse response = responseHolder.take();
            log.warn("send request is " + request);
            log.warn("receive response is " + response);
            channel.closeFuture();
            return response;
        } finally {
            group.shutdownGracefully();
        }

    }
}
