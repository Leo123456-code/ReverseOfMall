package com.imooc.sell.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * created by Leo徐忠春
 * created Time 2020/2/8-20:40
 * email 1437665365@qq.com
 */
@Data
public class ShippingAndForm {
    //收货姓名
    @NotBlank
    private String receiverName;
    //收货固定电话
    @NotBlank
    private String receiverPhone;
    //收货移动电话
    @NotBlank
    private String receiverMobile;
    //收货省份
    @NotBlank
    private String receiverProvince;
    //收货城市
    @NotBlank
    private String receiverCity;
    //收货区县
    @NotBlank
    private String receiverDistrict;
    //收货详细地址
    @NotBlank
    private String receiverAddress;
    //收货邮编
    @NotBlank
    private String receiverZip;

    public ShippingAndForm() {
    }

    public ShippingAndForm(@NotBlank String receiverName, @NotBlank String receiverPhone, @NotBlank String receiverMobile, @NotBlank String receiverProvince, @NotBlank String receiverCity, @NotBlank String receiverDistrict, @NotBlank String receiverAddress, @NotBlank String receiverZip) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverMobile = receiverMobile;
        this.receiverProvince = receiverProvince;
        this.receiverCity = receiverCity;
        this.receiverDistrict = receiverDistrict;
        this.receiverAddress = receiverAddress;
        this.receiverZip = receiverZip;
    }
}
