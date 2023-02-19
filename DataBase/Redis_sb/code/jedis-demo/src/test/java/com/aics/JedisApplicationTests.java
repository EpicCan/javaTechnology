package com.aics;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisApplicationTests {
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void contextLoads() {
        System.out.println(jedisPool);
        //在连接池中得到Jedis连接
        Jedis jedis = jedisPool.getResource();
        jedis.set("haha", "你好");
        jedis.set("name", "wangpengcheng");
        //关闭当前连接
        jedis.close();

    }
}