package com.imooc.sell.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-1:33
 * email 1437665365@qq.com
 */
@Data
public class UserLoginForm {

    //@NotBlank 可以判断空格
    @NotBlank(message = "名字不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
