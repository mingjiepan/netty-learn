package com.mjie.base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
/**
 * 用户数据报的编程模式
 */
public class BioServer2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(8899);

        byte[] buffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(buffer, 6);
        datagramSocket.receive(receivePacket);

        System.out.println("接收到数据 = " + new String(receivePacket.getData()));

        byte[] sendBuffer = "hello world".getBytes();
        DatagramPacket sendDatagram = new DatagramPacket(sendBuffer, sendBuffer.length);
        datagramSocket.connect(new InetSocketAddress("localhost", 8890));
        datagramSocket.send(sendDatagram);
    }
}

