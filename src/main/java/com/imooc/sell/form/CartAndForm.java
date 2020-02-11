package com.imooc.sell.form;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-17:35
 * email 1437665365@qq.com
 */

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 添加商品的表单
 */
@Data
public class CartAndForm {
    @NotNull
    //不能为空
    private Integer productId;
    //是否选中,默认设为true
    private Boolean selected = true;
}
