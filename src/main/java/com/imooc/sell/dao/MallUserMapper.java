package com.imooc.sell.dao;


import com.imooc.sell.pojo.MallUser;

public interface MallUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallUser record);

    int insertSelective(MallUser record);

    MallUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MallUser record);

    int updateByPrimaryKey(MallUser record);
    //查询有没有相同的名字
    int CountByUsername(String username);
    //查询有没有相同的名字
    int CountByEmail(String email);
    //通过用户名查询是否存在
    MallUser selectByUsername(String username);
}