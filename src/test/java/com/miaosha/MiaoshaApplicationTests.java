package com.miaosha;

import com.miaosha.redis.RedisPoolFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaApplicationTests {

    private static Logger testlog= LoggerFactory.getLogger(MiaoshaApplicationTests.class);
/*    @Autowired
    JedisPool jedisPool;*/

    @Autowired
    RedisPoolFactory redisPoolFactory;


    @Test
    public void contextLoads() {
    }





}
