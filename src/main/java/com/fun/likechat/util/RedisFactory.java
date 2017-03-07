package com.fun.likechat.util;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisFactory {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void update(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void incre(String key, long delta) {
        redisTemplate.opsForValue().increment(key, delta);
    }

    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    public void publish(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public void flushDB() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }
    
    public static void main(String[] args){
        ApplicationContext app = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        RedisFactory redisFactory = (RedisFactory) app.getBean("redisFactory");
        String[][] aa=new String[][]{new String[]{"1","aa"},new String[]{"17","bb"},new String[]{"20","cc"}};
        redisFactory.set("11", "11");
        
    }
    
}
