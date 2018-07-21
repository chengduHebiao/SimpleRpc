/**
 *  Inc All Rights Reserved @2018
 */

package com.cn.rpc.serialzation;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * 创建Jboss Marshalling 解码器和编码器
 *
 * @author hebiao
 * @version $Id:MarshallingCodeFactory.java, v0.1 2018/7/6 14:39 hebiao Exp $$
 */
public class MarshallingCodeFactory {

  /**
   * 解码器
   */
  public static MarshallingDecoder buildMarshallingDecoder() {
    final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
    final MarshallingConfiguration configuration = new MarshallingConfiguration();
    configuration.setVersion(5);
    UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
    MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024 * 1024 * 1);
    return decoder;

  }

  /**
   * 编码器
   *
   * @return MarshallingEncoder
   */
  public static MarshallingEncoder buildMarshallingEncoder() {
    final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
    final MarshallingConfiguration configuration = new MarshallingConfiguration();
    configuration.setVersion(5);
    MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
    //构建Netty的MarshallingEncoder对象，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
    MarshallingEncoder encoder = new MarshallingEncoder(provider);
    return encoder;
  }


}
