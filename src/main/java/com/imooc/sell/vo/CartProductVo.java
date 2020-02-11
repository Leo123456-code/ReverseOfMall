package com.imooc.sell.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-17:18
 * email 1437665365@qq.com
 */
@Data
public class CartProductVo {

    private Integer productId;
    //購買数量
    private Integer quantity;
    //商品名称
    private String productName;
    //商品标题
    private String productSubtitle;
    //商品主图
    private String productMainImage;
    //商品价格
    private BigDecimal productPrice;
    //商品状态
    private Integer productStatus;
    //商品总价 = quantity * productPrice
    private BigDecimal productTotalPrice;
    //商品库存
    private Integer productStock;
    //是否選中
    private Boolean productSelected;

    public CartProductVo() {
    }

    public CartProductVo(Integer productId, Integer quantity, String productName, String productSubtitle, String productMainImage, BigDecimal productPrice, Integer productStatus, BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubtitle = productSubtitle;
        this.productMainImage = productMainImage;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productSelected = productSelected;
    }
}
