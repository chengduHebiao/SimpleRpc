package com.cn.hebiao;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hebiao
 * @version $Id:NioDay1.java, v0.1 2018/5/20 17:18 hebiao Exp $$
 *
 * 数据从通道读入缓冲区，从缓冲区写入通道
 */
public class NioDay1 {

    public static void main(String[] args) throws Exception {

        RandomAccessFile file = new RandomAccessFile("F:\\demo.html", "rw");

        FileChannel channel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(48);

        int byteRead = channel.read(byteBuffer);//将数据从通道写入到buffer，也可以用buffer.put()方法进行操作

        while (byteRead != -1) {
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }

            byteBuffer.clear();
            byteRead = channel.read(byteBuffer);
        }
        file.close();
        readAndWrite();

    }

    public static void readAndWrite() {

        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(10);
        intBuffer.put(10);
        System.out.println("readMode:");
        System.out.println("capactity: " + intBuffer.capacity());
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());
        intBuffer.flip();//切换为读模式，将写模式下position的置为读模式下的limit 代表读模式下能读到的最大位置是写模式下已经写到的位置

        System.out.println("writeMode:");
        System.out.println("capactity: " + intBuffer.capacity());
        System.out.println("position: " + intBuffer.position());
        System.out.println("limit: " + intBuffer.limit());


    }

    public static void selector() throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            Set selectors = selector.selectedKeys();
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            Iterator<SelectionKey> it = selectors.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {

                } else if (key.isConnectable()) {

                } else if (key.isReadable()) {

                }
                it.remove();

            }

        }

    }

    public void createSocket() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();//等待新的客户端连接
            if (socketChannel != null) {
                System.out.println("new client ...ip " + socketChannel.getLocalAddress().toString());
            }
        }


    }


}
