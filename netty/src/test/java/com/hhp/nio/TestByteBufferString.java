package com.hhp.nio;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestByteBufferString {
    public static void main(String[] args) {
        // 字符串转为ByteBuffer
        //1. put
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        //2. Charset
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        //3. warp
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());

        //ByteBuffer转为字符串
        //
        String str2 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str2);
        //
        buffer1.flip();
        String str1 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str1);
    }
}
