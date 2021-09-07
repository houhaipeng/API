package com.hhp.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.hhp.utils.ByteBufferUtil.debugRead;

/**
 * selector->accept
 */
@Slf4j
public class Server2 {
    public static void main(String[] args) throws IOException {

        //1.创建selector,管理多个channel
        Selector selector = Selector.open();


//        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式
        //2.建立selector和channel的联系(注册)
        //SelectionKey就是将来事件发生后，通过它可以知道哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}", sscKey);

        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            //3. select方法,没有事件发生，线程阻塞，有事件，线程才会恢复
            //select在事件未处理时，他不会阻塞
            //事件发生后，要么处理，要么取消不能置之不理
            selector.select();
            //4.处理事件,selectKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //处理key时，要从selectedKeys集合中删除，否则下次处理就会有问题
                iterator.remove();
                log.debug("key:{}", key);
                //5. 区分事件类型
                if (key.isAcceptable()) {//如果是accept事件
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}", sc);
                } else if (key.isReadable()) {//如果是read事件
                    try {
                        //拿到触发事件的channel
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        channel.read(buffer);
                        buffer.flip();
                        debugRead(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();//因为客户端断开了，因此需要将key取消（从selector的key集合中真正删除key）
                    }
                }

//                key.cancel();
            }
        }
    }
}
