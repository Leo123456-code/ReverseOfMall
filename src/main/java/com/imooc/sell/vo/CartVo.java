package com.imooc.sell.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *  购物车VO
 * created by Leo徐忠春
 * created Time 2020/2/7-17:17
 * email 1437665365@qq.com
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    //是否全选
    private Boolean selectedAll;
    //所有商品总价
    private BigDecimal cartTotalPrice;
    //所有商品总数量
    private Integer cartTotalQuantity;

}
