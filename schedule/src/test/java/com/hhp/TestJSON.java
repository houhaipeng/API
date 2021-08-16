package com.hhp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class TestJSON {

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        map.put("amountToAdd", "15");
        map.put("unit", "天");
        String value = JSON.toJSONString(map);
        Map<String, String> map2 = JSON.parseObject(value, Map.class);
        System.out.println(map2);
        System.out.println(map2.get("unit"));

    }

    @Test
    public void test2() {
        String amountToAdd = "12";
    }
}
