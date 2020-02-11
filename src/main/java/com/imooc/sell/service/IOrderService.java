package com.imooc.sell.service;

import com.github.pagehelper.PageInfo;
import com.imooc.sell.vo.OrderVo;
import com.imooc.sell.vo.ResponseVo;

/**
 * created by Leo徐忠春
 * created Time 2020/2/9-16:58
 * email 1437665365@qq.com
 */
public interface IOrderService {
    /**
     * 创建订单
     */
    ResponseVo<OrderVo> create(Integer uid,Integer shippingId) throws Exception;
    /**
     * 分页查询订单列表
     */
    ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);
    /**
     * 订单详情
     */
    ResponseVo<OrderVo> detail(Integer uid,Long orderNo);
    /**
     * 订单取消
     */
    ResponseVo cancel(Integer uid,Long orderNo);
    //已支付
    void paid(Long orderNo);
}
