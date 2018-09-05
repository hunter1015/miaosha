package com.miaosha.interceptor;

import com.miaosha.entity.User;
import com.miaosha.redis.RedisService;
import com.miaosha.redis.UserKey;
import com.miaosha.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);

    @Autowired
    RedisService redisService;

    /**
     * 当访问path不在排除的路径集合中，会触发拦截器首先进入prehandle方法，对路径进行进一步筛选和处理，结合session等
      * @param request
     * @param response
     * @param handler
     * @return
     * @throws IOException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 这里写各种判断逻辑，如果没有………………，可以使用 reponse.send() 跳转页面。后面要跟return false,否则无法结束;

        String basePath = request.getContextPath();
        String path = request.getRequestURI();


        if(!doLoginInterceptor(path, basePath) ){//自定义方法，传入请求path和根path，验证是否进行登陆拦截
            return true;
        }






        //打印目前cookie状态和session状态
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            int i=1;
            for(Cookie cookie1 : cookies){
                logger.info("目前已存Cookie "+i+" 名为"+cookie1.getName()+" 值为"+cookie1.getValue());
                i++;
            }
        }
        HttpSession sessionpre = request.getSession();
        logger.info("目前已存session为"+sessionpre.getAttribute("cookietoken")+" 值为");


        //判断是否拦截的前提是，获取到当前cookie在session中能找到

        //获取名为“token”的cookie，只能有一个？？
        Cookie cookie=CookieUtil.getCookieByName(request,"token");
        boolean findflag=false;
        if (cookie!=null) {
            //获取具体的token值
            String token = cookie.getValue();
            findflag=redisService.exists(UserKey.token, token);
        }


        //源码的处理方式

        /*Cookie cookie= CookieUtil.getCookieByName(request,"token");
        //得到session，如果登录了，会把用户信息存进session
        HttpSession session=request.getSession(true);

        //获得session数据（这里涉及到session设置的知识，需要额外学习一下）
        Object user=session.getAttribute("username");
        //List<User> users =  (List<User>) session.getAttribute("userList");

        *//*User userInfo = new User();
        userInfo.setId(users.get(0).getId());
        userInfo.setName(users.get(0).getName());
        userInfo.setPassword(users.get(0).getPassword());*//*
        //开发环节的设置，不登录的情况下自动登录
        *//*if(userInfo==null && IGNORE_LOGIN){
            userInfo = sysUserService.getUserInfoByUserID(2);
            session.setAttribute(Constants.SessionKey.USER, userInfo);
        }*//*
*/

        if(findflag){
            //log.info("用户已登录,userName:"+userInfo.getSysUser().getUserName());
            logger.info("用户已登录,访问本路径，无需拦截");
            return true;
        }else{
            // 不存在则跳转到登录页 测试打印
            System.out.println("测试拦截器，获取本次request的访问路径"+request.getRequestURL().toString());


            /*log.info("尚未登录，跳转到登录界面");
            response.sendRedirect(request.getContextPath()+"signin");*/

            //获取header,获取请求类型
            String requestType = request.getHeader("X-Requested-With");
            //System.out.println(requestType);

            //如果请求类型不为空
            if(requestType!=null && requestType.equals("XMLHttpRequest")){
                response.setHeader("sessionstatus","timeout");
            //response.setHeader("basePath",request.getContextPath());
                response.getWriter().print("LoginTimeout");
                return false;
            } else {
                logger.info("尚未登录，跳转到登录界面");
                //设置跳转
                //response.sendRedirect(request.getContextPath()+"/login");
                response.sendRedirect(request.getContextPath()+"/login");
            }
            return false;
        }

    }
    /**
     * 通过判断访问path路径 是否进行登陆过滤
     * @param path
     * @param basePath
     * @return
     */
    private boolean doLoginInterceptor(String path,String basePath){

        //去除根路径，得到相对路径
        path = path.substring(basePath.length());

        //设置不允许登录的路径
        Set<String> notLoginPaths = new HashSet<>();
        //设置不进行登录拦截的路径：登录注册和验证码
        //notLoginPaths.add("/");
        notLoginPaths.add("/index");
        notLoginPaths.add("/signin");
        notLoginPaths.add("/login");
        notLoginPaths.add("/register");
        notLoginPaths.add("/kaptcha.jpg");
        notLoginPaths.add("/kaptcha");
        //notLoginPaths.add("/sys/logout");
        //notLoginPaths.add("/loginTimeout");

        if(notLoginPaths.contains(path)) return false;
        return true;
    }

    //    视图渲染之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {


    }
    //在请求处理之后,视图渲染之前执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
