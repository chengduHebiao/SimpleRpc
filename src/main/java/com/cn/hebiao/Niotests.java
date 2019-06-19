package com.cn.hebiao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hebiao
 * @version $Id:Niotests.java, v0.1 2018/7/11 16:29 hebiao Exp $$
 */
public class Niotests {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("F:\\demo.html");

        FileOutputStream fileOutputStream = new FileOutputStream("F:\\file.txt");

        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            byteBuffer.clear();
            int read = inChannel.read(byteBuffer);
            System.out.println(read);
            //读完了
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            outChannel.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }

}
