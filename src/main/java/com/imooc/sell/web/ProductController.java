package com.imooc.sell.web;

import com.github.pagehelper.PageInfo;
import com.imooc.sell.service.IProductService;
import com.imooc.sell.vo.ProductDetailVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-1:29
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseVo<PageInfo> products(@RequestParam(required = false) Integer categoryId,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false,defaultValue = "1") Integer pageSize){

        return productService.list(categoryId,pageNum,pageSize);
    }

    /**
     * 查询商品详情
     */
    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> productDetail(@PathVariable Integer productId){

        return productService.detail(productId);
    }
}
