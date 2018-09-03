package com.miaosha.controller;

import com.miaosha.entity.User;
import com.miaosha.result.Result;
import com.miaosha.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    @GetMapping("/info")
    public Result<User> info(Model model, User user){
        return Result.success(user);
    }

    @GetMapping("")
    public ModelAndView hello(HttpServletRequest httpServletRequest, Model model){
        String username=(String)httpServletRequest.getSession().getAttribute("username");
        model.addAttribute("username",username);
        return new ModelAndView("hello", "helloModel", model);
    }

    @GetMapping("/register")
    public ModelAndView createForm(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("title", "注册新用户");
        model.addAttribute("message", "");
        return new ModelAndView("new_user", "userModel", model);
    }



    @PostMapping("/register")
    private ModelAndView createUser(User user, Model model){
        //logger.info(user.getUsername());
        user.setRegisterDate(new Date(System.currentTimeMillis()));
        boolean ifsucc=userService.newUser(user);
        if(ifsucc) {
            model.addAttribute("username", user.getUsername());
            return new ModelAndView("redirect:/hello","helloModel",model);
            //show("hello", "helloModel", model);
        }else {
            model.addAttribute("error","用户重名");
            return createForm(model);
            //show("new_user", "userModel", model);
        }
    }


/*    private ModelAndView show(String html,String modelName,Model model){

        return new ModelAndView(html,modelName,model);

    }*/


/*
    private ModelAndView createUser(User user, Model model){
        //logger.info(user.getUsername());
        //user.setRegisterDate(new Date(System.currentTimeMillis()));
       // boolean ifsucc=userService.newUser(user);
        model.addAttribute("username",user.getUsername());
        //return new ModelAndView("redirect:/hello","helloModel",model);
        return new ModelAndView("hello","helloModel",model);
    }

    @PostMapping("/register")
    public void checkUser(User user,Model model){
        user.setRegisterDate(new Date(System.currentTimeMillis()));
        boolean ifsucc=userService.newUser(user);

        if (ifsucc){
            createUser(user, model);
        }else {
            model.addAttribute("message","创建用户失败！");
        }
    }*/
}
