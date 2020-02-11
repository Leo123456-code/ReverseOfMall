package com.imooc.sell.dao;


import com.imooc.sell.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    //根据id查询 订单列表
    List<Order> selectByUid(@Param("uid") Integer uid);
    //根据OrderNo 查询
    Order selectByOrderNo(@Param("orderNo") Long orderNo);

}