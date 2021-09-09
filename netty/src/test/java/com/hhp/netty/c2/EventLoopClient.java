package com.hhp.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        //1.启动器
        ChannelFuture channelFuture = new Bootstrap()
                //2.
                .group(new NioEventLoopGroup())
                //3.选择客户端channel
                .channel(NioSocketChannel.class)
                //4.添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override//在连接建立后被调用
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                //5.连接到服务器
                //异步非阻塞,main发起了调用,真正执行connect是nio线程
                .connect(new InetSocketAddress("localhost", 8080));//连接耗费一定时间
//        channelFuture.sync();
        //没有sync时，main线程无阻塞向下执行，获取channel,故为null
        Channel channel = channelFuture.channel();
        log.debug("{}", channel);
        //2. 向服务器发送数据
        channel.writeAndFlush("hello, world");
    }
}
