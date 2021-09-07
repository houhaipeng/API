package com.hhp.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.hhp.utils.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //使用nio来理解阻塞模式,单线程
        //0.ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //1.创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式
        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
        //3.连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            //4.accept,建立与客户端连接,socketChannel用来与客户端通信
            log.debug("connecting....");
            SocketChannel sc = ssc.accept();//阻塞方法,线程停止运行
            log.debug("connected....{}", sc);
            channels.add(sc);
            for (SocketChannel channel : channels) {
                //5.接收客户端发送的数据
                log.debug("before read...{}", channel);
                channel.read(buffer);//阻塞方法，线程停止运行
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }
}
