
package com.cn.hebiao.webSocket;

import java.io.IOException;
import java.util.Objects;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hebiao
 * @version $Id:WebSocketServer.java, v0.1 2018/9/21 15:14 hebiao Exp $$
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

  private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
  //与某个客户端的连接会话，需要通过它来给客户端发送数据
  private Session session;

  private static WebSocketManager webSocketManager;

  //解决无法用Autowired直接注入的问题
  @Autowired
  public void setWebSocketManager(WebSocketManager webSocketManager) {
    WebSocketServer.webSocketManager = webSocketManager;
  }


  /**
   * 连接建立成功调用的方法
   */
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    webSocketManager.addWebSocket("", this);
    try {
      sendMessage("连接成功");
    } catch (IOException e) {
      log.error("websocket IO异常");
    }
  }

  /**
   * 连接关闭调用的方法
   */
  @OnClose
  public void onClose() {
    webSocketManager.removeWebSocket("", this);
    log.info("有一连接关闭");
  }

  /**
   * 收到客户端消息后调用的方法
   *
   * @param message 客户端发送过来的消息
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("来自客户端的消息:" + message);
    webSocketManager.broadCastMessage(message, "");
  }

  /**
   *
   * @param session
   * @param error
   */
  @OnError
  public void onError(Session session, Throwable error) {
    log.error("发生错误");
    error.printStackTrace();
  }


  public void sendMessage(String message) throws IOException {
    this.session.getBasicRemote().sendText(message);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebSocketServer that = (WebSocketServer) o;
    return Objects.equals(session.getId(), that.session.getId());
  }

}
