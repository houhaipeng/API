package com.hhp;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.common.HttpResult;
import com.arronlong.httpclientutil.common.SSLs;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClientUtil {

    @Test
    public void test() throws HttpProcessException {
//        String url = "https://github.com/houhaipeng/API";
        String url = "https://www.baidu.com";
        //
//        String html = HttpClientUtil.get(HttpConfig.custom().url(url));T
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
        System.out.println(result1);
    }

    @Test
    public void test2() throws HttpProcessException {
        String url = "https://github.com/Arronlong/httpclientutil";

        //最简单的使用：
        String html = HttpClientUtil.get(HttpConfig.custom().url(url).client(HCB.custom().sslpv(SSLs.SSLProtocolVersion.TLSv1_2).ssl().build()));
        System.out.println(html);

        //---------------------------------
        //			【详细说明】
        //--------------------------------

        //插件式配置Header（各种header信息、自定义header）
        Header[] headers 	= HttpHeader.custom()
                .userAgent("javacl")
                .other("customer", "自定义")
                .build();

        //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
        HCB hcb 				= HCB.custom()
                //.timeout(1000) 		//超时
                .pool(100, 10)    	//启用连接池，每个路由最大创建10个链接，总连接数限制为100个
                .sslpv(SSLs.SSLProtocolVersion.TLSv1_2) 	//可设置ssl版本号，默认SSLv3，用于ssl，也可以调用sslpv("TLSv1.2")
                .ssl()  			   		//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
                .retry(5)					//重试5次
                ;

        HttpClient client = hcb.build();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)	//设置headers，不需要时则无需设置
                .timeout(1000) 		//超时
                .url(url)           //设置请求的url
                .map(map)			//设置请求参数，没有则无需设置
                .encoding("utf-8")  //设置请求和返回编码，默认就是Charset.defaultCharset()
                .client(client)     //如果只是简单使用，无需设置，会自动获取默认的一个client对象
                //.inenc("utf-8")   //设置请求编码，如果请求返回一直，不需要再单独设置
                //.inenc("utf-8")   //设置返回编码，如果请求返回一直，不需要再单独设置
                //.json("json字符串") //json方式请求的话，就不用设置map方法，当然二者可以共用。
                //.context(HttpCookies.custom().getContext())      //设置cookie，用于完成携带cookie的操作
                //.out(new FileOutputStream("保存地址"))              //下载的话，设置这个方法,否则不要设置
                //.files(new String[]{"d:/1.txt","d:/2.txt"})      //上传的话，传递文件路径，一般还需map配置，设置服务器保存路径
                ;


        //使用方式：
        String result1 = HttpClientUtil.get(config);    //get请求
        String result2 = HttpClientUtil.post(config);   //post请求
        System.out.println(result1);
        System.out.println(result2);

        //HttpClientUtil.down(config);                  //下载，需要调用config.out(fileOutputStream对象)
        //HttpClientUtil.upload(config);                //上传，需要调用config.files(文件路径数组)

        //如果指向看是否访问正常
        //String result3 = HttpClientUtil.head(config); // 返回Http协议号+状态码
        //int statusCode = HttpClientUtil.status(config);//返回状态码

        //[新增方法]sendAndGetResp，可以返回原生的HttpResponse对象，
        //同时返回常用的几类对象：result、header、StatusLine、StatusCode
        HttpResult respResult = HttpClientUtil.sendAndGetResp(config);
        System.out.println("返回结果：\n"+respResult.getResult());
        System.out.println("返回resp-header："+respResult.getRespHeaders());//可以遍历
        System.out.println("返回具体resp-header："+respResult.getHeaders("Date"));
        System.out.println("返回StatusLine对象："+respResult.getStatusLine());
        System.out.println("返回StatusCode："+respResult.getStatusCode());
        System.out.println("返回HttpResponse对象）（可自行处理）："+respResult.getResp());
    }
}
