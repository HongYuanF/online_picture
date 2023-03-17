package com.hongyuan.onlinepic.service.impl;

import com.hongyuan.onlinepic.model.User;
import com.hongyuan.onlinepic.repository.UserRepository;
import com.hongyuan.onlinepic.service.UserService;
import com.hongyuan.onlinepic.util.MailUtil;
import com.hongyuan.onlinepic.util.RedisUtil;
import com.hongyuan.onlinepic.util.Result;
import com.hongyuan.onlinepic.util.StrUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HongYuan
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    static RedisUtil redisUtilTmp;
    @Resource
    MailUtil mailUtil;
    static UserRepository userRepositoryTmp;
    @Resource
    RedisUtil redisUtil;

    public static boolean checkInDB(String userId) {
        return !userRepositoryTmp.findById(userId).isPresent();
    }

    public static boolean checkCode(String receiver, String code) {
        String codeInRedis = String.valueOf(redisUtilTmp.get("Action:" + receiver));
        if (codeInRedis == null) {
            return false;
        }
        return code.toLowerCase().equals(codeInRedis.toLowerCase());
    }

    @Override
    public Result register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Result.ok("注册成功");
    }

    /**
     * 通常我们会是在Spring框架中使用到@PostConstruct注解，该注解的方法在整个Bean初始化中的执行顺序：
     * Constructor（构造方法）—> @Autowired（依赖注入）—> @PostConstruct（注释的方法）
     */
    @PostConstruct
    public void init() {
        userRepositoryTmp = userRepository;
        redisUtilTmp = redisUtil;
    }

    /**
     * 获取激活码
     *
     * @param mail 需要激活的邮箱
     * @return
     */
    @Override
    public Result getActiveCode(String mail) {
        if (checkInDB(mail)) {
            sendRegisterMail(mail);
            return Result.ok("成功发送邮件");
        } else {
            return Result.fail("邮箱已被使用");
        }
    }

    public void sendRegisterMail(String receiver) {
        //发送邮件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String subject = "在线图床--用户注册";
        String emailTemplate = "registerTemplate";
        //获取激活码
        String code = StrUtil.RandomStringWithLength(5);
        //将激活码存入redis服务器
        //设置有效期10分钟  key值为:Action+邮箱 value值为:验证码
        redisUtil.setex("Action:" + receiver, code, 10 * 60);
//        System.out.println("key:"+"Action:"+receiver+"   value:"+code);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", receiver);
        dataMap.put("code", code);
        dataMap.put("createTime", sdf.format(new Date()));
        try {
            mailUtil.sendTemplateMail(receiver, subject, emailTemplate, dataMap);
            System.out.println("发送完成");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
