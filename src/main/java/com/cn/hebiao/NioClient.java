package com.cn.hebiao;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hebiao
 * @version $Id:NioClient.java, v0.1 2018/5/23 14:26 hebiao Exp $$
 */
public class NioClient {

    private static ByteBuffer receviedBuffer = ByteBuffer.allocate(2048);
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(2048);
    private static Selector selector;
    private static int flag = 0;

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost", 7080));
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                handler(key);
                it.remove();
            }
        }
    }

    private static void handler(SelectionKey key) throws IOException {
        String sendText;
        String receiveText;
        SocketChannel client;
        int count;
        if (key.isConnectable()) {
            System.out.println("client connect");
            client = (SocketChannel) key.channel();
            // 判断此通道上是否正在进行连接操作。
            // 完成套接字通道的连接过程。
            if (client.isConnectionPending()) {
                client.finishConnect();
                System.out.println("完成连接!");
                sendBuffer.clear();
                sendBuffer.put("Hello,Server".getBytes());
                sendBuffer.flip();
                client.write(sendBuffer);
                client.register(selector, SelectionKey.OP_READ);
            }
        } else if (key.isReadable()) {
            client = (SocketChannel) key.channel();
            receviedBuffer.clear();
            count = client.read(receviedBuffer);
            if (count > 0) {
                receiveText = new String(receviedBuffer.array(), 0, count);
                System.out.println("收到服务端的消息-->" + receiveText);
                client.register(selector, SelectionKey.OP_WRITE);
            }
        } else if (key.isWritable()) {
            client = (SocketChannel) key.channel();
            sendBuffer.clear();
            sendText = "message from client" + flag++;
            sendBuffer.put(sendText.getBytes());
            sendBuffer.flip();
            client.write(sendBuffer);
            System.out.println("客户端发送消息-->" + sendText);
            client.register(selector, SelectionKey.OP_READ);
        }
    }

}
