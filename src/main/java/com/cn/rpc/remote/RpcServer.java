/**
 * Inc All Rights Reserved @2018
 */

package com.cn.rpc.remote;

import com.cn.rpc.annotation.Rpc;
import com.cn.rpc.domain.RpcRequest;
import com.cn.rpc.domain.RpcResponse;
import com.cn.rpc.handler.RpcDecode;
import com.cn.rpc.handler.RpcEncode;
import com.cn.rpc.handler.RpcServerHandler;
import com.cn.rpc.handler.RpcProxyFactory;
import com.cn.rpc.interfaces.HelloService;
import com.cn.rpc.interfaces.HelloServiceImpl;
import com.cn.rpc.zookeeper.ZookeeperCreateFactory;
import com.cn.schedule.HostUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * @author hebiao
 * @version $Id:RpcServer.java, v0.1 2018/5/31 22:12 hebiao Exp $$
 */
@Component
public class RpcServer implements InitializingBean, ApplicationContextAware, Lifecycle {

  Logger log = LoggerFactory.getLogger(RpcServer.class);
  private Map<String, Object> serviceMap = new HashMap<>();
  private Integer port = 8099;

  public void afterPropertiesSet() {
    try {
      EventLoopGroup work = new NioEventLoopGroup();
      EventLoopGroup boss = new NioEventLoopGroup();
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap = serverBootstrap.group(boss, work);

      serverBootstrap.channel(NioServerSocketChannel.class);
      serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel ch) {
          ch.pipeline().addLast(new RpcDecode(RpcRequest.class))
              .addLast(new RpcEncode(RpcResponse.class)).addLast(new RpcServerHandler(serviceMap))
          ;
        }
      });

      serverBootstrap = serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
      serverBootstrap = serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
      ChannelFuture f = serverBootstrap.bind(port).sync();

      log.warn("已经启动rpc服务端");
      /**
       * 这里会一直等待，直到socket被关闭
       */
      //  RpcProxyFactory.init();
      f.channel().closeFuture().sync();
    } catch (Exception e) {

      log.error("start rpc server wrong");
    }


  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    Map<String, Object> annotationMap = applicationContext.getBeansWithAnnotation(Rpc.class);
    if (annotationMap != null) {
      annotationMap.keySet().forEach(key -> {
        Object bean = annotationMap.get(key);
        java.lang.Class<?> c = bean.getClass();
        java.lang.Class<?>[] inters = c.getInterfaces();
        String interfaceName = bean.getClass().getAnnotation(Rpc.class).value();
        for (Class clazz : inters) {
          if (clazz.getName().equals(interfaceName)) {
            log.warn("发布的服务{}", interfaceName);
            serviceMap.put(interfaceName, bean);
            ZookeeperCreateFactory.getZookeeper().createNode(interfaceName, HostUtils.getServerIp());
          }
        }

      });
    }
    //initServiceMap();

  }

  private void initServiceMap() {
    if (HelloServiceImpl.class.isAnnotationPresent(Rpc.class)) {
      Rpc rpc = HelloServiceImpl.class.getAnnotation(Rpc.class);
      serviceMap.put(rpc.value(), HelloServiceImpl.class);
    }
  }

  public void start() {

  }

  public void stop() {

  }

  public boolean isRunning() {
    return false;
  }
}
