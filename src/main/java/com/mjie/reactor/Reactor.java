package com.mjie.reactor;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 基础的reactor模式
 */
public class Reactor implements Runnable {

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    Reactor (int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            for (;;) {
                selector.select();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next());
                }
                selectionKeySet.clear();
            }
        } catch (Exception ex) {

        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            r.run();
        }
    }


    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Handler(selector, socketChannel);
                }
            } catch (Exception ex) {

            }
        }
    }
}



final class Handler implements Runnable {

    final SocketChannel socketChannel;
    final SelectionKey sk;

    ByteBuffer input = ByteBuffer.allocate(4094);
    ByteBuffer output = ByteBuffer.allocate(4094);

    static final int READING = 0, SENDING = 1;
    int state = READING;

    Handler (Selector sel, SocketChannel c)throws Exception {
        socketChannel = c;
        c.configureBlocking(false);

        sk = socketChannel.register(sel, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    boolean inputIsComplete() {
        return false;
    }

    boolean outputIsComplete() {
        return false;
    }

    void process(){}

    void read() throws Exception {
        socketChannel.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    void send() throws Exception {
        socketChannel.write(output);
        if (outputIsComplete()) {
            sk.cancel();
        }
    }

    @Override
    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (Exception ex) {

        }
    }
}