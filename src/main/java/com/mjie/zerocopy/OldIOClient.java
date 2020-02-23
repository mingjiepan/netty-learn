package com.mjie.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class OldIOClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8899);
        InputStream inputStream = new FileInputStream("/Users/panmingjie/Desktop/kafka.zip");
        byte[] buffer = new byte[4096];
        int length;
        int total = 0;
        OutputStream outputStream = socket.getOutputStream();

        long startTime = System.currentTimeMillis();

        while (-1 != (length = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, length);
            total += length;
        }
        System.out.println("往服务端写数据完毕，写的总字节数 = " + total + ", 耗时 = " + (System.currentTimeMillis() - startTime));
    }
}
