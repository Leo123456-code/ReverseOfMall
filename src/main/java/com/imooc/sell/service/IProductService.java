package com.imooc.sell.service;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-0:13
 * email 1437665365@qq.com
 */

import com.github.pagehelper.PageInfo;
import com.imooc.sell.vo.ProductDetailVo;
import com.imooc.sell.vo.ResponseVo;

/**
 * 商品列表
 */
public interface IProductService {
    //分页查询所有商品列表
    ResponseVo<PageInfo> list(Integer categoryId,
                              Integer pageNum, Integer pageSize);
    //根据id查询商品详情
    ResponseVo<ProductDetailVo> detail(Integer productId);

}
