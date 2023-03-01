package com.hongyuan.onlinepic.controller;

import com.hongyuan.onlinepic.configuration.ValidGroup;
import com.hongyuan.onlinepic.model.User;
import com.hongyuan.onlinepic.service.UserService;
import com.hongyuan.onlinepic.service.impl.UserServiceImpl;
import com.hongyuan.onlinepic.util.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author HongYuan
 */
@RestController
@RequestMapping("/user")
public class UserContoller  {
    @Resource
    UserService userService;


    @PostMapping("/register")
    public Result register(@RequestBody @Validated({ValidGroup.Update.class,ValidGroup.Insert.class}) User user) throws Exception {
        return userService.register(user);
    }

}
