package cn.bugstack.chatbot.api.infrastructure.service;


import cn.bugstack.chatbot.api.infrastructure.IRedisApi;
import cn.bugstack.chatbot.api.infrastructure.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisApi implements IRedisApi {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void setValue(String key, Object value) {
        RedisTemplate<String, Object> redisTemplate = redisUtils.getRedisTemplate();
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object getValue(String key) {
        RedisTemplate<String, Object> redisTemplate = redisUtils.getRedisTemplate();
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteValue(String key) {
        RedisTemplate<String, Object> redisTemplate = redisUtils.getRedisTemplate();
        redisTemplate.delete(key);
    }
}
