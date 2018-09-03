package com.miaosha.config;

import com.miaosha.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class LoginAdapter implements WebMvcConfigurer {
    /**
     * 设置不需要登录拦截的url:登录注册和验证码
     */
    final String[] notLoginInterceptPaths = {"/signin","/login/**","/index/**","/user/register","/kaptcha.jpg/**","/kaptcha/**","/test/**","/error/**","/js/**","/bootstrap/**",
            "/layer/**","/jquery-validation/**"};
    //"/", "/login/**", "/person/**", "/register/**", "/validcode", "/captchaCheck", "/file/**", "/contract/htmltopdf", "/questions/**", "/payLog/**", "/error/**" };
    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        // 登录拦截器
        // 添加一个拦截器，拦截以/test为前缀的 url路径 也可以排除掉一些路径
        //registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/goods/**");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);

    }

    /**
     * 目前不知道什么用
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }
}
