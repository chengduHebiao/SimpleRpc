/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author hebiao
 * @version $Id:HttpServerInitializer.java, v0.1 2019/1/29 15:58 hebiao Exp $$
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decode", new HttpRequestDecoder());
        pipeline.addLast("encode", new HttpResponseEncoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(1048576));
        pipeline.addLast("handler", new HttpServerHandler());
    }
}
