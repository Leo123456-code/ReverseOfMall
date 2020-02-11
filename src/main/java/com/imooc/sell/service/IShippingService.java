package com.imooc.sell.service;

import com.github.pagehelper.PageInfo;
import com.imooc.sell.form.ShippingAndForm;
import com.imooc.sell.vo.ResponseVo;

import java.util.Map;

/**
 * created by Leo徐忠春
 * created Time 2020/2/8-20:56
 * email 1437665365@qq.com
 */
public interface IShippingService {
    //新增地址
    ResponseVo<Map<String,Integer>> add(Integer uid, ShippingAndForm form);
    //删除
    ResponseVo delete(Integer uid,Integer shippingId);
    //更新地址
    ResponseVo update(Integer uid,Integer shippingId,ShippingAndForm form);
    //地址列表
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
