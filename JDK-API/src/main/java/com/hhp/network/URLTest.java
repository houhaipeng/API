package com.hhp.network;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class URLTest {

    public static void main(String[] args) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            //输入流
            inputStream = urlConnection.getInputStream();
            //输入流->输出流,通常用response的outputstream()
            fileOutputStream = new FileOutputStream("/Users/haipenghou/IdeaProjects/API/JDK-API/beauty3.jpg");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            System.out.println("下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //断开连接
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
