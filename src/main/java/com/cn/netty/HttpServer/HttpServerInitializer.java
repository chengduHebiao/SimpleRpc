/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer;

import com.cn.netty.HttpServer.converter.HttpDecode;
import com.cn.netty.HttpServer.converter.HttpEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author hebiao
 * @version $Id:HttpServerInitializer.java, v0.1 2019/1/29 15:58 hebiao Exp $$
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
       // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()));
        pipeline.addLast("decode", new HttpDecode());
        pipeline.addLast("encode", new HttpEncode());
        pipeline.addLast("handler", new HttpServerHandler());
    }
}
