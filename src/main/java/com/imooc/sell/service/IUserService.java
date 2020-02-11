package com.imooc.sell.service;

import com.imooc.sell.pojo.MallUser;
import com.imooc.sell.vo.ResponseVo;

/**
 * created by Leo徐忠春
 * created Time 2020/2/5-22:35
 * email 1437665365@qq.com
 */
public interface IUserService {
    /**
     * 注册
     */
    ResponseVo register(MallUser user);
    /**
     * 登录
     */
    ResponseVo<MallUser> login(String username,String password);
}
