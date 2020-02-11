package com.imooc.sell.service.Impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.DemoApplicationTests;
import com.imooc.sell.service.IProductService;
import com.imooc.sell.vo.ProductDetailVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-0:39
 * email 1437665365@qq.com
 */
@Slf4j
public class ProduceServiceImplTest extends DemoApplicationTests {
    @Autowired
    private IProductService productService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Integer CATEGORYID = 100002;
    private static final Integer PRODUCTID = 26;
    private static final Integer PAGENUM = 1;
    private static final Integer PAGESIZE = 10;

    @Test
    public void list() {
        ResponseVo<PageInfo> list = productService.list(CATEGORYID, PAGENUM, PAGESIZE);
        log.info("result={}",gson.toJson(list));
    }

    @Test
    public void detail() {
        ResponseVo<ProductDetailVo> detail = productService.detail(PRODUCTID);
        log.info("result={}",gson.toJson(detail));
    }

}