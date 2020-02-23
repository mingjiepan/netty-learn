package com.mjie.zerocopy;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NewIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(false);

        FileChannel fileChannel = new FileInputStream("/Users/panmingjie/Desktop/kafka.zip").getChannel();

        long startTime = System.currentTimeMillis();

        long transferTo = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送数据完毕，发送的字节数 = " + transferTo + "， 耗时 = " + (System.currentTimeMillis() - startTime));
    }
}
