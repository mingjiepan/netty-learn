package com.mjie.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.local.LocalAddress;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer1 {
    public static void main(String[] args)throws Exception {

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(8899));

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer byteBuffer = ByteBuffer.wrap("hello world".getBytes());

        for(;;) {
            selector.select();
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();
            while (selectionKeyIterator.hasNext()) {

                SelectionKey next = selectionKeyIterator.next();
                selectionKeyIterator.remove();


                try {
                    //new connection
                    if (next.isAcceptable()) {
                        serverSocketChannel = (ServerSocketChannel) next.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);

                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, byteBuffer.duplicate());
                    } else if (next.isWritable()) {

                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        ByteBuffer buffer = (ByteBuffer) next.attachment();

                        while (buffer.hasRemaining()) {
                            int write = socketChannel.write(buffer);
                            if (write == 0) {
                                break;
                            }
                        }
//                        socketChannel.close();

                    } else if (next.isReadable()) {

                        //读字节的时候，若客户端发送的是中文，那么就涉及到tcp的拆包与粘包了。不能一个字节一个字节的转换称字符。中文有可能涉及到多个字节。
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        while (socketChannel.read(readBuffer) != -1) {
                            System.out.println();
                        }

                    }

                } catch (Exception ex) {
                    next.cancel();
                    next.channel().close();
                }
            }
        }
    }
}
