package com.hongyuan.onlinepic.model;


import com.hongyuan.onlinepic.configuration.ValidGroup;
import com.hongyuan.onlinepic.repository.UserRepository;
import com.hongyuan.onlinepic.service.impl.UserServiceImpl;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Resource;
import javax.persistence.*;
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
     *
     * 可以包含小写大母 [a-z] 和大写字母 [A-Z]
     *
     * 可以包含数字 [0-9]
     *
     * 可以包含下划线 [ _ ] 和减号 [ - ]
     */
    @NotNull(message = "密码不能为NULL")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[\\w_-]{6,16}$",message = "密码设置过于简单",groups = {ValidGroup.Update.class,ValidGroup.Insert.class})
    private String password;


    @Length(message = "用户名长度设置不合理", min = 2, max = 10)
    @NotEmpty(message = "用户名不能为空")
    @NotNull(message = "用户名不能为空")
    private String username;


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)){
            return false;
        }
        User user = (User) o;
        return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @AssertTrue(message = "该邮箱已被使用",groups = {ValidGroup.Update.class,ValidGroup.Insert.class})
    public boolean isValid(){
        return UserServiceImpl.checkInDB(userId);
    }
}
