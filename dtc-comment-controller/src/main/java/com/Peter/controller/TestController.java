package com.Peter.controller;

import com.Peter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
   private UserService userService;
   @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
       //查询一下用户总人数
       int userTotal = userService.countUserTotal();
       log.trace("trace级别日志");
       log.debug("debug级别日志");
       log.info("info级别日志");
       log.warn("warn级别日志");
       log.error("error级别日志");
       //trace<debug<info<warn<error
        return "用户总人数："+userTotal;
    }
}
