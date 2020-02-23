package com.mjie.base;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于tcp的网络通信，还有基于UDP的编程模式，后面再处理。
 *
 * 问题，如何让阻塞的IO退出，从而能处理接下去的逻辑
 * 1. 关闭输出流 shutdownOutput
 * 2. 发送数据时，用一个固定长度的首部字段来表明整体数据的长度（包含首部的长度），当程序读到的字节数达到规定的之后，就不再read，退出IO的读取。
 * 3. 给socket设置一个超时时间，防止read一直挂起阻塞。
 */
public class BioServer1 {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);
        for(;;) {
            //实际的业务处理，在accept一个新的请求后，可以开启一个新的线程来处理，或者用线程池
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();

            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) != -1) {
//                System.out.println(new String(buffer, 0, length));
            }

            System.out.println("读取数据完毕");

            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("收到请求了".getBytes());
            outputStream.flush();

            //此处记得关闭输出流，否则会导致客户端的读取进程一直阻塞，无法退出程序
//            accept.shutdownOutput();
        }
    }
}
