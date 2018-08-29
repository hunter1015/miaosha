package com.miaosha;

import com.miaosha.entity.User;
import com.miaosha.redis.RedisPoolFactory;
import com.miaosha.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBtest {
    private static Logger dbtestlog= LoggerFactory.getLogger(DBtest.class);
/*    @Autowired
    JedisPool jedisPool;*/

    @Autowired
    RedisPoolFactory redisPoolFactory;

    @Autowired
    UserService userService;

    @Test
    public void insertTest(){
        User user=null;
        for (int i = 1; i < 11; i++) {
             user=new User();
             user.setUsername("测试用户"+i);
             user.setPassword("test"+i);
             user.setPhone(i<10?"1861065000"+i:"186106500"+i);
             userService.newUser(user);
            dbtestlog.info("第"+i+"次新建用户");

        }
    }
}
