package com.hongyuan.onlinepic.model;


import com.hongyuan.onlinepic.configuration.ValidGroup;
import com.hongyuan.onlinepic.service.impl.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * @author HongYuan
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    @NotNull(message = "用户名不能为空")
    @Email(message = "邮箱格式不正确")
    private String userId;


    /**
     * 最短6位，最长16位 {6,16}
     * 可以包含小写大母 [a-z] 和大写字母 [A-Z]
     * 可以包含数字 [0-9]
     * 可以包含下划线 [ _ ] 和减号 [ - ]
     */
    @NotNull(message = "密码不能为NULL")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[\\w_-]{6,16}$", message = "密码设置过于简单", groups = {ValidGroup.Insert.class})
    private String password;

    /**
     * 用于检验两次密码输入是否一致
     * 加入注解@Transient 持久化忽略该字段
     */
    @NotNull(message = "重复密码不能为空", groups = {ValidGroup.Insert.class})
    @Transient
    private String repeatPassword;


    @NotNull(message = "验证码不能为空", groups = {ValidGroup.Insert.class})
    @Length(min = 5, max = 5, message = "验证码长度不正确")
    @Transient
    private String code;

    /**
     * 当activeStatus为1时，该用户没有完成确认邮箱激活码
     * 当activeStatus为0时，该用户已通过认证
     */
    private int activeStatus = 1;
    @Length(message = "用户名长度设置不合理", min = 2, max = 10)
    @NotEmpty(message = "用户名不能为空")
    @NotNull(message = "用户名不能为空")
    private String username;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @AssertTrue(message = "两次密码输入不一致", groups = {ValidGroup.Update.class, ValidGroup.Insert.class})
    public boolean isPassword() {
        return password.equals(repeatPassword);
    }

    @AssertTrue(message = "该邮箱已被使用", groups = {ValidGroup.Update.class, ValidGroup.Insert.class})
    public boolean isValid() {
        return UserServiceImpl.checkInDB(userId);
    }

    @AssertTrue(message = "验证码不正确", groups = {ValidGroup.Update.class, ValidGroup.Insert.class})
    public boolean isCodeRight() {
        return UserServiceImpl.checkCode(userId, code);
    }
}
