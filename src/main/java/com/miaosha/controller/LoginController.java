package com.miaosha.controller;

import com.miaosha.redis.RedisService;
import com.miaosha.result.Result;
import com.miaosha.service.UserService;
import com.miaosha.vo.LoginVoPhone;
import com.miaosha.vo.LoginVoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private static Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @GetMapping("")
    public ModelAndView toLogin(Model model){
        model.addAttribute("loginVoUser",new LoginVoUser());
        return new ModelAndView("login","loginModel",model);
    }

    @PostMapping("do-login-phone")
    public Result<Boolean> doLogin(HttpServletRequest request,HttpServletResponse response, @Valid LoginVoPhone loginVoPhone){
        logger.info(loginVoPhone.toString());

        userService.loginByPhone(request,response,loginVoPhone);
        return Result.success(true);

    }

    @PostMapping("do-login-username")
    public Result<Boolean> doLogin(HttpServletRequest request,HttpServletResponse response, @ModelAttribute(value = "loginVoUser") LoginVoUser loginVoUser,Model model){

        //logger.info();
        //userService.
        boolean loginsuccess=userService.loginByUser(request,response,loginVoUser);
        return Result.success(loginsuccess);


    }

}
