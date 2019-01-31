/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer;

import com.cn.netty.HttpServer.domain.HRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hebiao
 * @version $Id:HttpServerHandler.java, v0.1 2019/1/29 16:19 hebiao Exp $$
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            String method = request.method().name();
            String url = request.uri().toString();
            String body = getBody(request);
            logger.warn("httpRequest:method: {},url:{},body:{}", method, url, body);

            String result;
            if (!"/test".equals(url)) {
                result = "not found";
                send(ctx, result, HttpResponseStatus.NOT_FOUND);
                return;
            }

            if (HttpMethod.GET.equals(request.method())) {
                result = "get请求";
                send(ctx, result, HttpResponseStatus.OK);
                return;
            }
            if (HttpMethod.POST.equals(request.method())) {
                result = "post请求";
                HttpHeaders headers = request.headers();
                Iterator<Entry<String, String>> iterator = headers.iteratorAsString();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = iterator.next();
                    logger.warn("header:" + entry.getKey() + "=" + entry.getValue() + "\r\n");
                }

                send(ctx, result, HttpResponseStatus.OK);
                return;
            }

        } catch (Throwable e) {
            logger.error(e.getMessage());
            send(ctx, "", HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }

        super.channelRead(ctx, msg);
    }

    private void send(ChannelHandlerContext ctx, String result, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer
                (result, CharsetUtil.UTF_8));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String getBody(FullHttpRequest request) {
        ByteBuf byteBuf = request.content();
        return byteBuf.toString(CharsetUtil.UTF_8);

    }

    //TODO 处理调用逻辑
    private void doInvoke(HRequest request) {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();//上线
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.warn("client [" + channel.remoteAddress().toString() + "]" + "与服务端建立连接成功\n");
        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.warn("client [" + channel.remoteAddress().toString() + "]" + "下线\n");
    }

}
