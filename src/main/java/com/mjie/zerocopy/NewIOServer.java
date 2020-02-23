package com.mjie.zerocopy;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8899));
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);

        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

        for (;;) {

            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            int readCount = 0;
            while (-1 != readCount) {
                readCount = socketChannel.read(byteBuffer);
                byteBuffer.rewind();
            }
        }
    }
}
