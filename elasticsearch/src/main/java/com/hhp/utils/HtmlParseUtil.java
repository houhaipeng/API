package com.hhp.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HtmlParseUtil {
    public static void main(String[] args) throws Exception {
        //获取请求https://search.jd.com/Search?keyword=java
        String url = "https://search.jd.com/Search?keyword=java";
        //解析网页.(Jsoup返回的Document就是浏览器Document对象)
        Document document = Jsoup.parse(new URL(url), 30000);
        //所有在js中可以使用的方法，这里都能用
        Element element = document.getElementById("J_goodsList");
        System.out.println(element.html());
    }

    public List<Object> parseJD(String keywords) throws IOException {
        //获取请求https://search.jd.com/Search?keyword=java
        String url = "https://search.jd.com/Search?keyword=java";
        //解析网页.(Jsoup返回的Document就是浏览器Document对象)
        Document document = Jsoup.parse(new URL(url), 30000);
        //所有在js中可以使用的方法，这里都能用
        Element element = document.getElementById("J_goodsList");
        System.out.println(element.html());
        return null;
    }
}
