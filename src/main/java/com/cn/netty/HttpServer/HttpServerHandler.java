/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer;

import com.cn.netty.HttpServer.domain.HRequest;
import com.cn.netty.HttpServer.domain.HResponse;
import com.cn.netty.HttpServer.domain.HResponse.CODE;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author hebiao
 * @version $Id:HttpServerHandler.java, v0.1 2019/1/29 16:19 hebiao Exp $$ 
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HRequest> {

    private static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HRequest request) throws Exception {

        logger.info("received request---->"+ request);

        doInvoke(request);

        HResponse response = new HResponse();

        response.setVersion("1.1");
        response.setStatus(CODE.OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setConnection("keep-alive");
        response.setDate(new Date());
        response.setServer("nginx/1.14.2");

        ctx.writeAndFlush(response);

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
        logger.warn("client [" + channel.remoteAddress().toString() + "]" + "上线\n");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.warn("client [" + channel.remoteAddress().toString() + "]" + "下线\n");
    }

}
