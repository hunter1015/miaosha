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
public class RedisTest {
    private static Logger redistestlog= LoggerFactory.getLogger(MiaoshaApplicationTests.class);
/*    @Autowired
    JedisPool jedisPool;*/

    @Autowired
    RedisPoolFactory redisPoolFactory;
    @Test
    public void Redistest() {
        redistestlog.info("测试-redis连接池");
        JedisPool jedisPool=redisPoolFactory.jedisPoolFactory();
        //testlog.info("测试-redis连接池-JedisPool "+jedisPool.);
        Jedis jedis = null;
        try {
            //jedis连接池
            jedis = jedisPool.getResource();
            redistestlog.info("测试-redis连接池-"+jedis.ping());
        } catch (Exception e) {
            redistestlog.error("测试-redis连接池-连接池报错！");
            e.printStackTrace();
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }
}
