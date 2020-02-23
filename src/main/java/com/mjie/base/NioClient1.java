package com.mjie.base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NioClient1 {
    public static void main(String[] args) throws Exception {

        Selector selector = Selector.open();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        boolean connect = socketChannel.connect(new InetSocketAddress("localhost", 8890));

        for (;;) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for (SelectionKey key : selectionKeys) {
                SelectableChannel channel = key.channel();

                if (key.isConnectable()) {
                    SocketChannel channel1 = (SocketChannel)channel;
                    boolean finishConnect = channel1.finishConnect();
                    if (finishConnect) {
                        System.out.println("链接到服务端了 to server");
                        channel1.write(ByteBuffer.wrap("你好，服务端，我是客户端".getBytes()));
                        channel1.register(selector, SelectionKey.OP_READ);
                    }
                } else if (key.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                    int read = socketChannel.read(byteBuffer);
                    if (read > 0) {
                        byteBuffer.flip();
                        while (byteBuffer.hasRemaining()) {
                            System.out.print((char)byteBuffer.get());
                        }
                    }
                }
                selectionKeys.remove(key);
            }
        }
    }
}
