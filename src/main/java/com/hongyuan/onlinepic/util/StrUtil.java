package com.hongyuan.onlinepic.util;

import java.util.UUID;

/**
 * @author HongYuan
 * @description TODO
 * 提供关于String一些函数
 * 1.生成可选长度的随机字符串
 * @date 2023-03-15 22:29
 */
public class StrUtil {


    /**
     * @param length 需要的长度
     * @return 长度为length的随机字符串
     */
    public static String RandomStringWithLength(int length) {
        return usingUUID().substring(0, length);
    }

    private static String usingUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "");
    }
}
