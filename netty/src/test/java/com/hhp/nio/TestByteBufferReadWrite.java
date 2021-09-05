package com.hhp.nio;

import com.hhp.utils.ByteBufferUtil;

import java.nio.ByteBuffer;


public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);//'a'
        ByteBufferUtil.debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        ByteBufferUtil.debugAll(buffer);
        buffer.flip();
        System.out.println(buffer.get());
    }
}
