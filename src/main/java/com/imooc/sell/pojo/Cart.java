package com.imooc.sell.pojo;

import lombok.Data;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-21:43
 * email 1437665365@qq.com
 */
@Data
public class Cart {

    private Integer productId;
    //購買数量
    private Integer quantity;
    //是否選中
    private Boolean productSelected;

    public Cart() {
    }

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
