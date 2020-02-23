package com.mjie.base;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BioClient1 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8899);

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("你好，我来了".getBytes());

        //若不关闭输出流，那么服务端在获取收入流时，会一直阻塞直到有数据可以读取，但是读取完数据后，又在等待数据，陷入一个死循环，而无法处理接下去的逻辑，
        //因此记得要关闭输出流，记住，关闭的是输出流，而不是socket
//        socket.shutdownOutput();

        /*InputStream inputStream = socket.getInputStream();

        int length = 0;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer)) > 0) {
            System.out.println(new String(buffer, 0, length));
        }*/

        System.out.println("读取服务端返回的数据成功");
    }
}
