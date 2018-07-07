/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.rpc.handler;

import com.cn.rpc.SerialzationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @author hebiao
 * @version $Id:RpcDecode.java, v0.1 2018/6/1 10:29 hebiao Exp $$
 */
public class RpcDecode extends ByteToMessageDecoder {

  private Class<?> generClass;

  public RpcDecode(Class<?> generClass) {
    this.generClass = generClass;
  }

  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if (in.readableBytes() < 4) {
      return;
    }
    in.markReaderIndex();
    int dataLength = in.readInt();
    if (dataLength < 0) {
      ctx.close();
    }
    if (in.readableBytes() < dataLength) {
      in.resetReaderIndex();
    }
    byte[] data = new byte[dataLength];
    in.readBytes(data);
    Object object = SerialzationUtil.deserialize(data);
    out.add(object);
  }
}
