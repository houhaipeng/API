package com.hhp.controller;

import com.hhp.entity.User;
import com.hhp.mapper.UserMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/list")
    public List<User> listUser() {
        List<User> users = userMapper.selectList(null);
        return users;
    }
}
