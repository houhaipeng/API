package com.hhp.zk;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest {

    @Test
    public void test() {
        Map<String, User> map = new HashMap<>();
        User user1 = new User("语言");
        User user2 = new User("操作系统");
        map.put("java", user1);
        map.put("linux", user2);
        System.out.println(map);
        User user = map.get("java");
        user.setDescription("编程语言");
        System.out.println(map);
    }
}
