package com.mjie.common;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

public class Tools {
    public static void echoBuffer(ByteBuffer byteBuffer) {

        if (byteBuffer != null) {
            System.out.println("position = " + byteBuffer.position());
            System.out.println("limit = " + byteBuffer.limit());
            System.out.println("capacity = " + byteBuffer.capacity());
            System.out.println("---------");
        }
    }


    public static void echoByteBuf(ByteBuf byteBuf) {
        if (byteBuf != null) {
            System.out.println("writeIndex  = " + byteBuf.writerIndex());
            System.out.println("readIndex = " + byteBuf.readerIndex());
            System.out.println("capacity = " + byteBuf.capacity());
            System.out.println("hasArray = " + byteBuf.hasArray());
            System.out.println("---------");
        }
    }
}
