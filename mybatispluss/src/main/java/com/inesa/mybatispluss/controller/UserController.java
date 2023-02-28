package com.inesa.mybatispluss.controller;

import com.inesa.mybatispluss.entity.User;
import com.inesa.mybatispluss.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("list")
    public void test(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}

