package com.imooc.sell.service.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.DemoApplicationTests;
import com.imooc.sell.form.CartAndForm;
import com.imooc.sell.form.CartUpdateFrom;
import com.imooc.sell.service.ICartService;
import com.imooc.sell.vo.CartVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-21:52
 * email 1437665365@qq.com
 */
@Slf4j
public class CartServiceImplTest extends DemoApplicationTests {
    @Autowired
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Integer UID = 1;
    private static final Integer PRODUCTID = 27 ;
    private static final Boolean SELECTED = true ;
    private static final Integer QUANTITY = 5 ;

    @Test
    public void add() {
        CartAndForm form = new CartAndForm();
        form.setProductId(PRODUCTID);
        form.setSelected(SELECTED);
        ResponseVo<CartVo> responseVo = cartService.add(UID, form);
        log.info("result={}",gson.toJson(responseVo));
    }

    @Test
    public void list(){
        ResponseVo<CartVo> responseVo = cartService.list(UID);
        log.info("result={}", gson.toJson(responseVo));
    }

    @Test
    public void update(){
        CartUpdateFrom update = new CartUpdateFrom();
        update.setQuantity(QUANTITY);
        update.setSelected(SELECTED);
        ResponseVo<CartVo> responseVo = cartService.update(UID, PRODUCTID, update);
        log.info("result={}",gson.toJson(responseVo));
    }

    @Test
    public void delete(){
        ResponseVo<CartVo> responseVo = cartService.delete(UID, PRODUCTID);
        log.info("result={}",gson.toJson(responseVo));

    }

    @Test
    public void selectAll(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.selectAll(UID);
        log.info("result={}",gson.toJson(cartVoResponseVo));


    }
    @Test
    public void unselectAll(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.unselectAll(UID);
        log.info("result={}",gson.toJson(cartVoResponseVo));

    }
    @Test
    public void sum(){
        ResponseVo<Integer> sum = cartService.sum(UID);
        log.info("result={}",sum);

    }
}