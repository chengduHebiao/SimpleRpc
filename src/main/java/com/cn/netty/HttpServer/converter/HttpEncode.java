/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer.converter;

import com.cn.netty.HttpServer.common.CommonUtils;
import com.cn.netty.HttpServer.domain.HResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hebiao
 * @version $Id:HttpEncode.java, v0.1 2019/1/29 17:18 hebiao Exp $$
 */
public class HttpEncode extends MessageToByteEncoder {

    private static Logger logger = LoggerFactory.getLogger(HttpEncode.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        logger.info("返回客户端的消息：" + CommonUtils.POINT + msg);
        StringBuilder stringBuilder = new StringBuilder();
        HResponse response = (HResponse) msg;
        stringBuilder.append(response.getVersion() + " " + response.getStatus().name() + "\r\n")
                .append("server" + ": " + response.getServer() + "\r\n")
                .append("Date: " + response.getDate() + "\r\n")
                .append("ContentType: " + response.getContentType() + "\r\n")
                .append("Connection: " + response.getConnection());

        byte[] bytes = stringBuilder.toString().getBytes(Charset.forName("utf8"));
        out.writeBytes(bytes);

    }

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 ").append("200");
        System.out.println(stringBuilder.toString());
    }
}
