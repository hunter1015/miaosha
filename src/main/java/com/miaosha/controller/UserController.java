package com.miaosha.controller;

import com.miaosha.entity.User;
import com.miaosha.result.Result;
import com.miaosha.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    public ModelAndView hello(Model model){
        model.addAttribute("username",userService.getUserById(20).getUsername());
        return new ModelAndView("hello", "helloModel", model);
    }

    @GetMapping("/register")
    public ModelAndView createForm(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("title", "注册新用户");
        return new ModelAndView("new_user", "userModel", model);
    }
    @PostMapping("/register")
    public ModelAndView createUser(User user, Model model){
        logger.info(user.getUsername());
        user.setRegisterDate(new Date(System.currentTimeMillis()));
        userService.newUser(user);
        model.addAttribute("username",user.getUsername());
        //return new ModelAndView("redirect:/hello","helloModel",model);
        return new ModelAndView("hello","helloModel",model);
    }
}
