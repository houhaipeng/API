package com.hhp.nio;

import com.hhp.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();

//        buffer.get(new byte[4]);
//        ByteBufferUtil.debugAll(buffer);
//        //
//        buffer.rewind();
//        System.out.println((char) buffer.get());
        //mark & reset
        System.out.println((char) buffer.get());//'a'
        System.out.println((char) buffer.get());//'b'
        buffer.mark();//加标记，索引2的位置
        System.out.println((char) buffer.get());//'c'
        System.out.println((char) buffer.get());//'d'
        buffer.reset();
        System.out.println((char) buffer.get());//'c'
        System.out.println((char) buffer.get());//'d'

    }
}
