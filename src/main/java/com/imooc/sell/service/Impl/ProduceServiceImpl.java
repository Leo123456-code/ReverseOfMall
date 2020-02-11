package com.imooc.sell.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.dao.ProductMapper;
import com.imooc.sell.pojo.Product;
import com.imooc.sell.service.ICategoryService;
import com.imooc.sell.service.IProductService;
import com.imooc.sell.vo.ProductDetailVo;
import com.imooc.sell.vo.ProductVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.imooc.sell.enums.ProductStatusEnum.DELETE;
import static com.imooc.sell.enums.ProductStatusEnum.OFF_SALE;
import static com.imooc.sell.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-0:16
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class ProduceServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICategoryService categoryService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 分页查询所有商品列表
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum,
                                            Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId!=null){
            categoryService.findSubsCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        //查询所有
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList =
                productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList = new ArrayList<>();
        //将 Product 转化为 ProductVo
        ProductVo productVo = new ProductVo();
        for (Product product : productList) {
            BeanUtils.copyProperties(product,productVo);
            productVoList.add(productVo);
        }
//        List<Product> productList =
//               productMapper.selectByCategoryIdSet(categoryIdSet);
//        List<ProductVo> productVoList = productList.stream()
//                .map(e -> {
//                    ProductVo productVo = new ProductVo();
//                    BeanUtils.copyProperties(e, productVo);
//                    return productVo;
//                })
//                .collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo<>(productVoList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //如果商品下架或者删除,直接返回错误
        if(product.getStatus().equals(OFF_SALE.getCode())
                ||product.getStatus().equals(DELETE.getCode())){
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        //对敏感数据 库存的处理
        //如果库存数据大于100,就显示100,小于100,就实际显示
        productDetailVo.setStock(product.getStock()>100 ? 100 :product.getStock());


        return ResponseVo.success(productDetailVo);
    }
}
