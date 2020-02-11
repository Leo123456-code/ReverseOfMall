package com.imooc.sell.vo;

import lombok.Data;

import java.util.Date;

/**
 * created by Leo徐忠春
 * created Time 2020/2/8-21:11
 * email 1437665365@qq.com
 */
@Data
public class ShippingVo {

    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 收货姓名
     */
    private String receiverName;
    /**
     * 收货固定电话
     */
    private String receiverPhone;
    /**
     * 收货移动电话
     */
    private String receiverMobile;
    /**
     * 省份
     */
    private String receiverProvince;
    /**
     * 城市
     */
    private String receiverCity;
    /**
     * 区/县
     */
    private String receiverDistrict;
    /**
     * 详细地址
     */
    private String receiverAddress;

    /**
     * 邮编
     */
    private String receiverZip;

    private Date createTime;

    private Date updateTime;
}
