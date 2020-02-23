package com.mjie.base;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class BioClient2 {
    public static void main(String[] args) throws Exception{
        DatagramSocket datagramSocket = new DatagramSocket(8890);

        byte[] sendBuffer = "welcome".getBytes();
        DatagramPacket sendDatagram = new DatagramPacket(sendBuffer, sendBuffer.length);
        datagramSocket.connect(new InetSocketAddress("localhost", 8899));
        datagramSocket.send(sendDatagram);

        System.out.println("---------");

        byte[] buffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(buffer, 5);
        datagramSocket.receive(receivePacket);

        System.out.println("接收到数据 = " + new String(receivePacket.getData(), 0, 11));
    }
}
