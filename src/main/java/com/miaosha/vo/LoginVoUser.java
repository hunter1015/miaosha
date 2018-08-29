package com.miaosha.vo;

import com.miaosha.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/*接受参数*/
@Getter
@Setter
@ToString
public class LoginVoUser {
    @NotNull
    @Length(min = 6)
    private String username;
    @NotNull
    @Length(min = 6)
    private String password;
}
