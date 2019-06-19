/**
 * Inc All Rights Reserved @2018
 */

package com.cn.rpc.handler;

import com.cn.rpc.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author hebiao
 * @version $Id:RpcEncode.java, v0.1 2018/6/1 10:22 hebiao Exp $$ 编码器，将字节转为对象
 */
public class RpcEncode extends MessageToByteEncoder {

    private Class<?> generaClass;

    public RpcEncode(Class<?> clazz) {
        this.generaClass = clazz;
    }


    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (generaClass.isInstance(msg)) {
            byte[] data = SerializationUtil.serialize(msg);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
