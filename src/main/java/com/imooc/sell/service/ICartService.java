package com.imooc.sell.service;

import com.imooc.sell.form.CartAndForm;
import com.imooc.sell.form.CartUpdateFrom;
import com.imooc.sell.pojo.Cart;
import com.imooc.sell.vo.CartVo;
import com.imooc.sell.vo.ResponseVo;

import java.util.List;

/**
 *    购物车接口
 * created by Leo徐忠春
 * created Time 2020/2/7-21:16
 * email 1437665365@qq.com
 */
public interface ICartService {
    /**
     * 购物车添加商品
     */
    ResponseVo<CartVo> add(Integer uid,CartAndForm from);
    /**
     * 购物车列表信息
     */
    ResponseVo<CartVo> list(Integer uid);
    /**
     * 更新购物车
     */
    ResponseVo<CartVo> update(Integer uid, Integer productId,
                              CartUpdateFrom from);
    /**
     * 移除购物车的某个商品
     */
    ResponseVo<CartVo> delete(Integer uid,Integer productId);
    //全选中
    ResponseVo<CartVo> selectAll(Integer uid);
    //全不选中
    ResponseVo<CartVo> unselectAll(Integer uid);
    //获取购物中所有商品数量总和
    ResponseVo<Integer> sum(Integer uid);
    //获取购物车
    List<Cart> listForCart(Integer uid);
}
