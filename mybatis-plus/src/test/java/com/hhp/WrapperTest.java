package com.hhp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhp.entity.User;
import com.hhp.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .ge("age", 25);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test2() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "Tom");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void test3() {

    }
}
