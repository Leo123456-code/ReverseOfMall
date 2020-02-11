package com.imooc.sell.service.Impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.DemoApplicationTests;
import com.imooc.sell.form.ShippingAndForm;
import com.imooc.sell.service.IShippingService;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * created by Leo徐忠春
 * created Time 2020/2/8-22:24
 * email 1437665365@qq.com
 */
@Slf4j
public class ShippingServiceImplTest extends DemoApplicationTests {

    private static final Integer UID = 10 ;
    private static final Integer PAGENUMBER= 1 ;
    private static final Integer PAGESIZE = 5 ;
    private static final Integer SHIPPINGID = 12 ;
    private static final String receiverName="张国立";
    private static final String receiverPhone="027-11111111";
    private static final String receiverMobile="15555552222";
    private static final String receiverProvince="湖北";
    private static final String receiverCity="武汉";
    private static final String receiverDistrict="洪山区";
    private static final String receiverAddress="曙光星城9栋208";
    private static final String receiverZip="435400";




    @Autowired
    private IShippingService shippingService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {

        ShippingAndForm form =
                new ShippingAndForm(receiverName,receiverPhone,receiverMobile,
                        receiverProvince,receiverCity,
                        receiverDistrict,receiverAddress,receiverZip);

        ResponseVo<Map<String, Integer>> map = shippingService.add(UID, form);
        log.info("result={}",gson.toJson(map));
    }

    @Test
    public void delete() {
        ResponseVo responseVo = shippingService.delete(UID, SHIPPINGID);
        log.info("result={}",gson.toJson(responseVo));
    }

    @Test
    public void update() {

        ShippingAndForm form =
                new ShippingAndForm(receiverName,receiverPhone,receiverMobile,
                        receiverProvince,receiverCity,
                        receiverDistrict,receiverAddress,receiverZip);
        ResponseVo responseVo = shippingService.update(UID, SHIPPINGID, form);
        log.info("result={}",gson.toJson(responseVo));
    }

    @Test
    public void list() {
        ResponseVo<PageInfo> list = shippingService.list(UID, PAGENUMBER, PAGESIZE);
        log.info("result={}",gson.toJson(list));

    }
}