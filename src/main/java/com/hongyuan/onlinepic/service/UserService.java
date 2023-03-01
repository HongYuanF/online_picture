package com.hongyuan.onlinepic.service;

import com.hongyuan.onlinepic.model.User;
import com.hongyuan.onlinepic.util.Result;
import org.springframework.stereotype.Service;

/**
 * @author HongYuan
 */

public interface UserService {
    Result register(User user);
}
