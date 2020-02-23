package com.mjie.zerocopy;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OldIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);

        for (;;) {
            Socket accept = serverSocket.accept();

            InputStream inputStream = accept.getInputStream();

            byte[] buffer = new byte[2048];
            int length = 0;
            int total = 0;
            while (-1  != (length = inputStream.read(buffer))) {
                total += length;
            }
            System.out.println("读取客户端发送的数据完毕, 读取的字节数 = " + total);
        }
    }
}
