package com.imooc.sell.service.Impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.DemoApplicationTests;
import com.imooc.sell.enums.ResponseEnum;
import com.imooc.sell.form.CartAndForm;
import com.imooc.sell.service.ICartService;
import com.imooc.sell.service.IOrderService;
import com.imooc.sell.vo.CartVo;
import com.imooc.sell.vo.OrderVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * created by Leo徐忠春
 * created Time 2020/2/9-18:03
 * email 1437665365@qq.com
 */
@Slf4j
public class OrderServiceImplTest extends DemoApplicationTests {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ICartService cartService;

    private static final Integer UID = 1;
    private static final Integer SHIPPINGID = 8;

    private static final Integer PRODUCTID = 26 ;
    private static final Boolean SELECTED = true ;
    private static final Integer QUANTITY = 5 ;


    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAndForm form = new CartAndForm();
        form.setProductId(PRODUCTID);
        form.setSelected(SELECTED);
        ResponseVo<CartVo> responseVo = cartService.add(UID, form);
        log.info("result={}",gson.toJson(responseVo));
    }


    private ResponseVo<OrderVo> create() throws Exception{
        Map<String,Object> params = new HashMap<>();
        params.put("uid",UID);
        params.put("shippingid",SHIPPINGID);
        ResponseVo<OrderVo> orderVoResponseVo =
                orderService.create(SHIPPINGID,UID);

        log.info("result={}",gson.toJson(orderVoResponseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),
                orderVoResponseVo.getStatus());
        return orderVoResponseVo;
    }

    @Test
    public void createTest() throws Exception{
        Map<String,Object> params = new HashMap<>();
        params.put("uid",UID);
        params.put("shippingid",SHIPPINGID);
        ResponseVo<OrderVo> orderVoResponseVo =
                orderService.create(SHIPPINGID,UID);

        log.info("result={}",gson.toJson(orderVoResponseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),
                orderVoResponseVo.getStatus());

    }

    @Test
    public void list() throws Exception{
        ResponseVo<PageInfo> list = orderService.list(UID, 1, 10);
        log.info("list={}",gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),
                list.getStatus());
    }
    @Test
    public void detail() throws Exception {
      ResponseVo<OrderVo> vo = create();
      ResponseVo<OrderVo> detail = orderService.detail(UID,
              vo.getData().getOrderNo());
      log.info("detail={}",gson.toJson(detail));

    }
    @Test
    public void cancel() throws Exception {
      ResponseVo<OrderVo> vo = create();
        ResponseVo responseVo = orderService.cancel(UID, vo.getData().getOrderNo());
        log.info("cancel={}",gson.toJson(responseVo));

    }
}