package com.imooc.sell.form;

/**
 * created by Leo徐忠春
 * created Time 2020/2/6-0:18
 * email 1437665365@qq.com
 */

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 表单
 */
@Data
public class UserRegisterForm {
    //@NotBlank 可以判断空格
    @NotBlank(message = "名字不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    private String email;



}
