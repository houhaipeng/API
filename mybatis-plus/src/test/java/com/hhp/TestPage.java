package com.hhp;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhp.entity.User;
import com.hhp.mapper.UserMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPage {

    @Resource
    private UserMapper userMapper;

    @Test
    public void test() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Page<User> page = new Page<>();
        userMapper.selectPage(page, wrapper);
    }
}
