package com.hhp.io;

import org.junit.Test;

import java.io.*;

/**
 * 缓冲流
 *
 * 结论：
 * 1. 对于文本文件，使用字符流处理
 * 2. 对于非文本文件（图片），使用字节流处理
 */
public class BufferTest {

    @Test
    public void BufferedStreamTest() throws FileNotFoundException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //1. 造文件
            File srcFile = new File("Docker.md");
            File destFile = new File("Docker_copy3.md");
            //2. 造流
            //2.1 造节点流
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            //2.2 造缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);
            //3. 复制的细节：读取，写入
            //小车长度为3
            byte[] buffer = new byte[4];
            int len;
            //read()返回每次读取到byte[]数组中的个数，如果bis有11个byte,则第一次返回10，第二次返回1，第三次(已经没有数据了)返回-1
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
                System.out.println(len);//3 3 3 1
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.资源关闭:
            //要求：先关闭外层的流，再关闭内层的流
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //说明：关闭外层流的同时，内层流也会自动进行关闭。关于内存流的关闭，我们可以省略
        //即只需要关闭bufferInputStream,bufferOutputStream
//        fis.close();
//        fos.close();

    }
}
