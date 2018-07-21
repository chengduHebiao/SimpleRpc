/**
 *  Inc All Rights Reserved @2018
 */

package com.cn.rpc.handler;

import com.cn.rpc.domain.RpcRequest;
import com.cn.rpc.domain.RpcResponse;
import com.cn.rpc.interfaces.HelloService;
import com.cn.rpc.remote.RpcServer;
import java.lang.reflect.Proxy;
import java.util.UUID;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

/**
 * @author hebiao
 * @version $Id:RpcProxyFactory.java, v0.1 2018/6/1 11:49 hebiao Exp $$
 */
@Component
@ConditionalOnClass(value = {RpcServer.class})
public class RpcProxyFactory implements InitializingBean{

  /**
   * 引用服务
   *
   * @param <T> 接口泛型
   * @param interfaceClass 接口类型
   * @param host 服务器主机名
   * @param port 服务器端口
   * @return 远程服务
   */
  @SuppressWarnings("unchecked")
  public static <T> T create(final Class<T> interfaceClass, final String host, final int port)
      {
    if (interfaceClass == null || !interfaceClass.isInterface()) {
      throw new IllegalArgumentException("必须指定服务接口");
    }

    if (host == null || host.length() == 0) {
      throw new IllegalArgumentException("必须指定服务器的地址和端口号");
    }

    return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
        new Class<?>[]{interfaceClass}, (proxy, method, arguments) -> {
          RpcRequest request = new RpcRequest();
          request.setId(UUID.randomUUID().toString());
          request.setInterfaceName(interfaceClass.getName());
          request.setMethodName(method.getName());
          request.setClazzTypes(method.getParameterTypes());
          request.setParameters(arguments);
          RpcResponse response = new RpcRequestSender().send(request, host, port);

          if (response == null
              || !StringUtils.equals(request.getId(), response.getRequestId())) {
            return null;
          }
          if (response.getError() != null) {
            throw response.getError();
          }
          return response.getResult();

        });

  }

  public static void main(String[] args) throws Exception {
    HelloService service = RpcProxyFactory.create(HelloService.class, "127.0.0.1", 8099);
    service.sayHello("中国人");
  }

  /**
   * 启动客户端
   * @throws Exception
   */
  public  static void init() throws Exception {
    HelloService service = RpcProxyFactory.create(HelloService.class, "127.0.0.1", 8099);
    while (true){
      service.sayHello("中国人");
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {

    //init();
  }
}
