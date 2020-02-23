package com.mjie.base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class NioServer1 {
    public static void main(String[] args)throws Exception {

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8890));
        serverSocketChannel.configureBlocking(false);

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        for (;;) {
            int select = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {

                SelectableChannel selectableChannel = key.channel();

                if (key.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel)selectableChannel;
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);

                    System.out.println("accept new client");
                } else if (key.isReadable()) {

                    System.out.println("read data from client channel");

                    SocketChannel socketChannel = (SocketChannel)selectableChannel;

                    ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                    int read = socketChannel.read(byteBuffer);

                    if (read > 0) {
                        byteBuffer.flip();
                        while (byteBuffer.hasRemaining()) {
                            System.out.print((char)byteBuffer.get());
                        }
                    }

                    socketChannel.write(ByteBuffer.wrap("welcome".getBytes()));
                }
                selectionKeys.remove(key);
            }
        }
    }
}
