package com.imooc.sell.service.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.dao.MallUserMapper;
import com.imooc.sell.enums.ResponseEnum;
import com.imooc.sell.enums.RoleEnum;
import com.imooc.sell.pojo.MallUser;
import com.imooc.sell.service.IUserService;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * created by Leo徐忠春
 * created Time 2020/2/5-22:37
 * email 1437665365@qq.com
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    private MallUserMapper userMapper;

    /**
     * 注册
     * @param user
     */
    @Override
    public ResponseVo register(MallUser user) {

        //效验
        //username和email不能重复
        int countByUseraname = userMapper.CountByUsername(user.getUsername());
        if(countByUseraname>0){
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        int countByEmail = userMapper.CountByEmail(user.getEmail());
        if(countByEmail>0){
            return ResponseVo.error(ResponseEnum.EMAIL_ERROR);
        }
        //  MD5加密密码
        String password = DigestUtils.md5DigestAsHex
                (user.getPassword().getBytes(StandardCharsets.UTF_8));
        //密码MD5加密
        user.setPassword(password);
        user.setRole(RoleEnum.CUSTOMER.getCode());//设置角色
        int result = userMapper.insertSelective(user);
        if(result==0){
           ResponseVo.error(ResponseEnum.ERROR);
        }
       return ResponseVo.success();//注册成功
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResponseVo<MallUser> login(String username, String password) {
        MallUser user = userMapper.selectByUsername(username);
        if(user == null){
            return ResponseVo.error(ResponseEnum.USER_OR_PASSWORD_FAIL);
        }
        //密码不一致
        if(!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            return ResponseVo.error(ResponseEnum.USER_OR_PASSWORD_FAIL);
        }
        user.setPassword("");//密码隐藏
        return ResponseVo.success(user);
    }
}
