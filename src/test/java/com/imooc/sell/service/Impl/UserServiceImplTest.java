package com.imooc.sell.service.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.DemoApplicationTests;
import com.imooc.sell.enums.ResponseEnum;
import com.imooc.sell.enums.RoleEnum;
import com.imooc.sell.pojo.MallUser;
import com.imooc.sell.service.IUserService;
import com.imooc.sell.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by Leo徐忠春
 * created Time 2020/2/5-22:58
 * email 1437665365@qq.com
 */

//加在测试类中执行成功后回滚
public class UserServiceImplTest extends DemoApplicationTests {
    private static final String USERNAME = "孙455";
    private static final String PASSWORD = "xu55214881..";
    private static final String EMAIL = "xu143548848789@163.com";


    @Autowired
    private IUserService userService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void register() {
        MallUser user = new MallUser(USERNAME, PASSWORD, EMAIL, RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }
    @Test
    public void login() {
        ResponseVo<MallUser> responseVo =
                userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),
                responseVo.getStatus());
    }
}