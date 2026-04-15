package com.Peter.controller;

import com.Peter.service.UserService;
import com.Peter.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
   private UserService userService;
   @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String query(){
       //查询一下用户总人数
       int userTotal = userService.countUserTotal();
        return "用户总人数："+userTotal;
    }
}
