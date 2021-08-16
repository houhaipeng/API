package com.hhp;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws HttpProcessException {
//        String url = "https://github.com/Arronlong/httpclientutil";
        //
        String url = "https://www.baidu.com";
//        String html = HttpClientUtil.get(HttpConfig.custom().url(url));
//        System.out.println(html);

        Header[] headers = HttpHeader.custom()
                .userAgent("javacl")
                .other("customer", "自定义")
                .build();

        HCB hcb = HCB.custom()
                .pool(100, 10)//启动连接池
                .sslpv(SSLs.SSLProtocolVersion.TLSv1_2)
                .ssl();

        HttpClient client = hcb.build();

        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(url)
                .map(map)
                .encoding("utf-8")
                .client(client);
        String result1 = HttpClientUtil.get(config);
        System.out.println("=====================");
        System.out.println(result1);
    }

    @Test
    public void test() {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        System.out.println(stringObjectHashMap.keySet().contains("a"));
    }
}
