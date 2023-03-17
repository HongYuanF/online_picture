package com.hongyuan.onlinepic.controller;

import com.hongyuan.onlinepic.configuration.ValidGroup;
import com.hongyuan.onlinepic.model.User;
import com.hongyuan.onlinepic.service.UserService;
import com.hongyuan.onlinepic.util.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author HongYuan
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Resource
    UserService userService;


    @PostMapping("/register")
    public Result register(@RequestBody @Validated({ValidGroup.Insert.class, ValidGroup.Update.class}) User user) throws Exception {
        return userService.register(user);
    }


    @PostMapping("/getActiveCode")
    public Result getActiveCode(@RequestBody @NotNull(message = "用户名不能为空") @Email(message = "邮箱格式错误") String mail) {
        return userService.getActiveCode(mail);
    }
}
