package com.hongyuan.onlinepic.service;

import com.hongyuan.onlinepic.model.User;
import com.hongyuan.onlinepic.util.Result;

/**
 * @author HongYuan
 */

public interface UserService {
    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    Result register(User user);

    /**
     * 获取激活码
     *
     * @param mail 需要激活的邮箱
     * @return
     */
    Result getActiveCode(String mail);
}
