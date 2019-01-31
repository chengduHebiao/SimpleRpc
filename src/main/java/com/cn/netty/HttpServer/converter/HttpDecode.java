/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.netty.HttpServer.converter;

import com.cn.netty.HttpServer.common.CommonUtils;
import com.cn.netty.HttpServer.common.IdentityAnnotation;
import com.cn.netty.HttpServer.domain.HRequest;
import com.cn.netty.HttpServer.domain.HRequest.METHOD;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * http协议解码器
 *
 * @author hebiao
 * @version $Id:HttpDecode.java, v0.1 2019/1/29 17:18 hebiao Exp $$
 */
public class HttpDecode extends ByteToMessageDecoder {

    private static Logger logger = LoggerFactory.getLogger(HttpDecode.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        logger.info("开始解码http请求:" + CommonUtils.POINT + msg.toString());
        byte[] byteAars = new byte[msg.readableBytes()];
        msg.readBytes(byteAars);
        String result = new String(byteAars);
        logger.info("解析出的请求数据：" + CommonUtils.POINT + result);
        HRequest request = new HRequest();//构造请求对象
        buildRequest(request, result);
        out.add(request);
    }

    private void buildRequest(HRequest request, String originSource) throws IOException, IllegalAccessException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream
                (originSource.getBytes(Charset.forName("utf8")))));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            doSolveLine(line, request);

        }

    }

    private void doSolveLine(String line, HRequest request) throws IllegalAccessException {
        Field[] fields = HRequest.class.getDeclaredFields();
        if (line.contains("GET") && line.contains("HTTP")) {
            String method = line.substring(0, line.indexOf("/")).trim();
            String uri = line.substring(line.indexOf("/"), line.indexOf("HTTP")).trim();
            request.setUrl(uri);
            METHOD method1 = METHOD.valueOf(method);
            request.setMethod(method1);
            return;
        }
        for (Field field : fields) {
            Object value;
            IdentityAnnotation identityAnnotation = field.getAnnotation(IdentityAnnotation.class);
            if (identityAnnotation != null) {
                if (line.contains(identityAnnotation.value())) {
                    value = line.substring(line.indexOf(":"), line.length()).trim();
                    field.setAccessible(true);
                    field.set(request, value);
                }
            }
        }


    }
}
