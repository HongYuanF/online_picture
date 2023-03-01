package com.hongyuan.onlinepic.service.impl;

import com.hongyuan.onlinepic.model.User;
import com.hongyuan.onlinepic.repository.UserRepository;
import com.hongyuan.onlinepic.service.UserService;
import com.hongyuan.onlinepic.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author HongYuan
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    static UserRepository userRepositoryTmp;


    /**
     * 通常我们会是在Spring框架中使用到@PostConstruct注解，该注解的方法在整个Bean初始化中的执行顺序：
     * Constructor（构造方法）—> @Autowired（依赖注入）—> @PostConstruct（注释的方法）
     */
    @PostConstruct
    public void init() {
        userRepositoryTmp = userRepository;
    }
    @Override
    public Result register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Result.ok("注册成功");
    }

    public static boolean checkInDB(String userId){
        return !userRepositoryTmp.findById(userId).isPresent();
    }
}
