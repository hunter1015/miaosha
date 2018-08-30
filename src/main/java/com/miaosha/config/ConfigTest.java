package com.miaosha.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


//三种方法，介绍属性配置，其中前两种都需要借助properties属性文件，第三种是直接在程序启动参数中调节


//方法1  映射application.properties文件变量，利用前缀
@ConfigurationProperties(prefix = "testconfig")//方法1：映射我们在 application.properties 中的内容

//方法2  自定义properties文件，并映射自定义文件的变量，同样利用前缀
/*@PropertySource("classpath:my2.properties")
@ConfigurationProperties(prefix = "my2")*/


//方法3 外部引导，设置命令参数修改属性配置（用于生产发布）
//默认情况下，SpringApplication 会将命令行选项参数（即：–property，如–server.port=9000）添加到Environment，命令行属性始终优先于其他属性源。
//例如：“java -jar chapter2-0.0.1-SNAPSHOT.jar --spring.profiles.active=test --my1.age=32”
public class ConfigTest {

    private String name; //这里的name等于properties文件中testconfig.name的值


    //方法4，通过value直接引用，不知道对不对
    @Value(value="${person.name}")
    private String personName;


}
