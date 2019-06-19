package com.cn.hebiao.webSocket;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author hebiao
 * @version $Id:WebSocketManager.java, v0.1 2018/9/21 15:23 hebiao Exp $$
 */
@Component
public class WebSocketManager {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);
    private static final String DEFAULT_TOPIC = "default";
    /**
     * 所有的连接对象
     */
    private static Map<String, CopyOnWriteArraySet<WebSocketServer>> allWebSocketServer = Maps.newConcurrentMap();

    /***
     * 缓存客户端websocket对象
     * @param topic
     * @param webSocketServer
     */
    public void addWebSocket(String topic, WebSocketServer webSocketServer) {

        if (StringUtils.isEmpty(topic)) {
            topic = DEFAULT_TOPIC;
        }
        CopyOnWriteArraySet<WebSocketServer> servers = allWebSocketServer.get(topic);
        if (servers == null || servers.isEmpty()) {
            servers = new CopyOnWriteArraySet();
            servers.add(webSocketServer);
        } else {
            servers.add(webSocketServer);
        }
        allWebSocketServer.put(topic, servers);

    }

    /***
     *  清楚客户端
     * @param topic
     * @param webSocketServer
     */
    public void removeWebSocket(String topic, WebSocketServer webSocketServer) {
        if (StringUtils.isEmpty(topic)) {
            topic = DEFAULT_TOPIC;
        }
        CopyOnWriteArraySet<WebSocketServer> servers = allWebSocketServer.get(topic);
        if (servers != null && !servers.isEmpty()) {
            servers.remove(webSocketServer);
            allWebSocketServer.put(topic, servers);
        }
    }

    /**
     * 广播消息
     */
    public void broadCastMessage(String message, String topic) {
        if (StringUtils.isEmpty(topic)) {
            topic = DEFAULT_TOPIC;
        }
        Set<WebSocketServer> webSocketServers = allWebSocketServer.get(topic);
        try {
            for (WebSocketServer webSocketServer : webSocketServers) {
                webSocketServer.sendMessage(message);
            }
        } catch (Exception e) {
            LOG.error("广播消息出错", e);
        }
    }

    /**
     * 向特定的客户端发送消息
     */
    public void sendSpecial(String message, String topic, WebSocketServer webSocketServer) {
        Set<WebSocketServer> webSocketServers = allWebSocketServer.get(topic);
        try {
            for (WebSocketServer server : webSocketServers) {
                if (server.equals(server)) {
                    server.sendMessage(message);
                }
            }
        } catch (Exception e) {
            LOG.error("发送消息出错", e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        allWebSocketServer.clear();
    }

}
