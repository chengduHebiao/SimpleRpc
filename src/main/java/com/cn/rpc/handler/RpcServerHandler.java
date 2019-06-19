/**
 * Inc All Rights Reserved @2018
 */

package com.cn.rpc.handler;

import com.cn.rpc.domain.RpcRequest;
import com.cn.rpc.domain.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hebiao
 * @version $Id:RpcServerHandler.java, v0.1 2018/6/1 9:40 hebiao Exp $$
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Logger logger = LoggerFactory.getLogger(RpcServerHandler.class);

    private Map<String, Object> serviceMap;

    public RpcServerHandler(Map<String, Object> serviceMap) {

        this.serviceMap = serviceMap;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        RpcResponse response = new RpcResponse();
        try {
            response = handle(msg);
        } catch (Exception e) {
            response.setError(e);
        }

        ctx.writeAndFlush(response);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    private RpcResponse handle(RpcRequest request) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getId());
        Object clazz = serviceMap.get(request.getInterfaceName());
        if (clazz == null) {
            throw new Exception("cant get request class");
        }
        try {
            Method method = clazz.getClass().getDeclaredMethod(request.getMethodName(), request.getClazzTypes());
            Object result = method.invoke(clazz, request.getParameters());
            response.setResult(result);
        } catch (NoSuchMethodException e) {
            logger.error("error", e);
        } catch (IllegalAccessException e) {
            logger.error("error", e);
        } catch (InvocationTargetException e) {
            logger.error("error", e);
        }
        return response;
    }
}
