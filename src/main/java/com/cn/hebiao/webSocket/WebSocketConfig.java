/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.hebiao.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author hebiao
 * @version $Id:WebSocketConfig.java, v0.1 2018/9/21 16:55 hebiao Exp $$
 */
@Configuration
public class WebSocketConfig {

  /**
   * 此类用于暴露serverEndPoint
   * @return
   */
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
}
