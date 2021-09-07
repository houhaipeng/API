package com.hhp.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        //1. 启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2.selector+thread,
                .group(new NioEventLoopGroup())
                //3.选择服务器的ServerSocketChannel的实现
                .channel(NioServerSocketChannel.class)
                //4.接下来添加的处理器都是给 SocketChannel 用的，而不是给 ServerSocketChannel
                //ChannelInitializer 处理器（仅执行一次），它的作用是待客户端 SocketChannel 建立连接后，执行 initChannel 以便添加更多的处理器
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        //SocketChannel 的处理器，解码 ByteBuf => String
                        channel.pipeline().addLast(new StringDecoder());
                        //SocketChannel 的业务处理器，使用上一个处理器的处理结果
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {//自定义handler
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //打印上一步转换好的字符串
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //ServerSocketChannel 绑定的监听端口
                .bind(8080);
    }
}
