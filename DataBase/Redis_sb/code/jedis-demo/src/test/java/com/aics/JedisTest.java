package com.aics;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 创建日期: 2022/05/24 15:19
 *
 * @author ciggar
 */
public class JedisTest {

    static Jedis jedis;


    static {

//        Connection  connection = DriverManager.getConnection(String url,String username,String password);


        jedis = new Jedis("ip", 6379);
        jedis.auth("123456");


    }

    @Test
    public void redis() {
        // 1.string
        //输出结果: OK
        jedis.set("hello", "world");
        //输出结果: world
        jedis.get("hello");
        //输出结果:1
        jedis.incr("counter");


        // 2.hash
        jedis.hset("myhash", "f1", "v1");
        jedis.hset("myhash", "f2", "v2");
        //输出结果 : {f1=v1, f2=v2}
        jedis.hgetAll("myhash");

        // 3.list
        jedis.rpush("mylist", "1");
        jedis.rpush("mylist", "2");
        jedis.rpush(" mylist", "3");
        //输出结果 : [1, 2, 3]
        jedis.lrange("mylist", 0, -1);


        // 4.set
        jedis.sadd(" myset", "a");
        jedis.sadd(" myset", "b");
        jedis.sadd(" myset", "a");
        //输出结果 : [b, a]
        jedis.smembers("myset");

        // 5.zset
        jedis.zadd("myzset", 99, "tom");
        jedis.zadd("myzset", 66, "peter");
        jedis.zadd("myzset", 33, "james");
        //输出结果 : [[["james"],33.0], [["peter"],66.0], [["tom"],99.0]]
        jedis.zrangeWithScores("myzset", 0, -1);

    }

    @Test
    public void testJedis() {

        jedis.set("nickname", "张三");


    }


    @Test
    public void testJedis2() {

        String nickname = jedis.get("nickname");

        System.out.println(nickname);

    }
}
