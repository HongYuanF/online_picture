package com.hongyuan.onlinepic.configuration;

import javax.validation.GroupSequence;

/**
 * @author HongYuan
 * @description TODO
 * @date 2023-03-13 2:00
 */
public class ValidGroup {

    /**
     * 新增使用(配合spring的@Validated功能分组使用)
     */
    public interface Insert{}

    /**
     * 更新使用(配合spring的@Validated功能分组使用)
     */
    public interface Update{}

    /**
     * 删除使用(配合spring的@Validated功能分组使用)
     */
    public interface Delete{}

    public interface Save{}

    /**
     * 属性必须有这两个分组的才验证(配合spring的@Validated功能分组使用)
     */
    @GroupSequence({Insert.class, Update.class,Delete.class,Save.class})
    public interface All{}
}
