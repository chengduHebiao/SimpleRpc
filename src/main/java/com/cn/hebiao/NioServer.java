package com.cn.hebiao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于NIO的服务端
 *
 * @author hebiao
 * @version $Id:NioServer.java, v0.1 2018/5/22 15:48 hebiao Exp $$
 */
public class NioServer {

    private static int flag = 0;
    private Selector selector;
    private ByteBuffer receiveBuffer = ByteBuffer.allocate(2048);//用于处理接收的channel
    private ByteBuffer sendBuffer = ByteBuffer.allocate(2048);//用于处理发送的channel

    public NioServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//设置为非阻塞
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        selector = Selector.open();
        //注册channel到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("has start server -->" + port);

    }

    public static void main(String[] args) throws IOException {
        int port = 7080;
        NioServer server = new NioServer(7080);
        server.listen();
    }

    /**
     * 监听事件
     */

    private void listen() throws IOException {
        while (true) {
            selector.select();//select()阻塞到至少有一个通道在你注册的事件上就绪了。
            Set<SelectionKey> selectionKey = selector.selectedKeys();//返回所有已经就绪的通道
            Iterator<SelectionKey> it = selectionKey.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                handleKey(key);
                it.remove();//注意每次迭代末尾的it.remove()调用。Selector不会自己从已选择键集中移除SelectionKey实例。必须在处理完通
                // 道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
            }
        }
    }

    /**
     * 业务逻辑处理
     */
    private void handleKey(SelectionKey key) throws IOException {
        ServerSocketChannel server;
        SocketChannel client;
        String receiveText;
        String sendTex;
        int count;
        if (key.isAcceptable()) {
            // 返回为之创建此键的通道。
            server = (ServerSocketChannel) key.channel();
            // 接受到此通道套接字的连接。
            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            client = server.accept();
            // 配置为非阻塞
            client.configureBlocking(false);
            // 注册到selector，等待连接
            client.register(selector, SelectionKey.OP_READ);
        }
        if (key.isConnectable()) {
            server = (ServerSocketChannel) key.channel();
            client = server.accept();
            client.configureBlocking(false);
            System.out.println("新的客户端已经连接。。。ip是" + client.getLocalAddress().toString());
            client.register(selector, SelectionKey.OP_READ);

        } else if (key.isReadable()) {
            client = (SocketChannel) key.channel();
            receiveBuffer.clear();
            count = client.read(receiveBuffer);
            if (count > 0) {
                receiveText = new String(receiveBuffer.array(), 0, count);
                System.out.println("客户端发来消息----->" + receiveText);
                client.register(selector, SelectionKey.OP_WRITE);
            }
        } else if (key.isWritable()) {
            sendBuffer.clear();
            client = (SocketChannel) key.channel();
            sendTex = "send a message to client" + flag++;
            sendBuffer.put(sendTex.getBytes());
            sendBuffer.flip();
            client.write(sendBuffer);
            System.out.println("向客户端发送消息" + sendTex);
            client.register(selector, SelectionKey.OP_READ);

        }

    }

}
