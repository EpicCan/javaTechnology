package com.inesa.mybatispluss;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.inesa.mybatispluss.entity.User;
import com.inesa.mybatispluss.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootTest
class MybatisplussApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private User user;


    @Test
    void contextLoads() {
    }


    // select
    @Test
    public void select(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

    }


    @Test
    public void update(){

        // 要修改的记录
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("name","Jack");

        // 将name为Jack的age修改为18
        User user = new User();
        user.setAge(18);
        int update = userMapper.update(user, userUpdateWrapper);
        System.out.println(update);
    }


    // insert
    @Test
    public void insert(){
        User user = new User();
        user.setId(6);
        user.setName("Rose");
        user.setAge(18);
        user.setEmail("123");
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }



    // delete
    @Test
    public void delete(){

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",6);

        int delete = userMapper.delete(userQueryWrapper);
        System.out.println(delete);
    }
}
