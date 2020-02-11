package com.imooc.sell.dao;


import com.imooc.sell.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);
    //新增
    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
    //删除订单
    int deleteByIdAndUid(@Param("uid") Integer uid,
                         @Param("shippingId") Integer shippingId);
    //根据userId查询
    List<Shipping> selectByUid(@Param("uid") Integer uid);

    //根据Uid和ShippingId查询
    Shipping selectByUidAndShippingId(Integer shippingId,Integer uid);

    //根据userId查询
    List<Shipping> selectByIdSet(@Param("shippingIdSet") Set<Integer> shippingIdSet );


}