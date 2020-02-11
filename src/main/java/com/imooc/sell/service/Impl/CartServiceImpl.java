package com.imooc.sell.service.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.dao.ProductMapper;
import com.imooc.sell.form.CartAndForm;
import com.imooc.sell.form.CartUpdateFrom;
import com.imooc.sell.pojo.Cart;
import com.imooc.sell.pojo.Product;
import com.imooc.sell.service.ICartService;
import com.imooc.sell.vo.CartProductVo;
import com.imooc.sell.vo.CartVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.imooc.sell.enums.ProductStatusEnum.ON_SALE;
import static com.imooc.sell.enums.ResponseEnum.*;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-21:19
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Gson g = new Gson();

    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    /**
     * 购物车添加商品
     * @param from
     * @return
     */
    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAndForm from) {
        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey
                (from.getProductId());
        //商品是否存在
        if(product==null){
            return ResponseVo.error(PRODUCT_NOT_EXIST);//商品不存在
        }
        //商品是否正常在售
        if(!product.getStatus().equals(ON_SALE.getCode())){
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);//商品下架或删除
        }
        //商品库存是否充足
        if(product.getStock()<=0){
            return ResponseVo.error(PRODUCT_STOCK_NOT_MALL);
        }
        //加入redis
        //key:cart_1,cart_2;
        //redis存入 productId,quantity,selected 等字段
        //存储 String
//        redisTemplate.opsForValue()
//                .set(String.format(CART_REDIS_KEY_TEMPLATE,uid),
//                        g.toJson(new Cart(product.getId(),quantity,from.getSelected())));
       //存储Hash
        //<key,key,value> 1.key redis的key
        //2.key hash 的key
        //3.value
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey,
                String.valueOf(product.getId()));
        Cart cart ;
        if(StringUtils.isEmpty(value)){
            //没有该商品
            cart = new Cart(product.getId(),quantity,from.getSelected());
        }else {
            //如果有了,数量加1
            cart = g.fromJson(value,Cart.class);
            cart.setQuantity(cart.getQuantity()+1);
        }
        opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE,uid),
                String.valueOf(product.getId()),g.toJson(cart));



        return ResponseVo.success();
    }

    /**
     * 从redis中读取购物车信息
     * 购物车列表信息
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash
                = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<CartProductVo> cartProductVoList = new ArrayList<>();
        CartVo cartVo = new CartVo();
        boolean selectAll = true;
        Integer cartTotalQuantity = 0 ;
        BigDecimal cartTotalPrice =  new BigDecimal(0);
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = g.fromJson(entry.getValue(),Cart.class);

            //TODO需要优化,使用mysql里的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if(product!=null){
                CartProductVo cartProductVo = new CartProductVo(
                        productId,cart.getQuantity(),product.getName(),
                        product.getSubtitle(),product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),cart.getProductSelected());
                //敏感数据处理
                cartProductVo.setProductStock(cartProductVo.getProductStock() >100 ? 100 : cartProductVo.getProductStock());
                cartProductVoList.add(cartProductVo);

                if(!cart.getProductSelected()){
                    selectAll = false;
                }
                //计算总价(只计算选中的)
                if(cart.getProductSelected()){
                    cartTotalPrice = cartTotalPrice.add
                            (cartProductVo.getProductTotalPrice());
                    cartTotalQuantity += cart.getQuantity();
                }

            }

//            cartTotalQuantity += cart.getQuantity();
        }


        //是否全选
        cartVo.setSelectedAll(selectAll);
        //购物车总数量
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        //购物车总价格
        cartVo.setCartTotalPrice(cartTotalPrice);

        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    /**
     * 更新购物车
     */
    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId,
                                     CartUpdateFrom from) {
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey,
                String.valueOf(productId));

        if(StringUtils.isEmpty(value)){
            //没有该商品,报错
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);//购物车无此商品
        }
            //如果有了,修改内容
            Cart cart = g.fromJson(value,Cart.class);
            //数量不为0
            if(from.getQuantity() != null
             && from.getQuantity() >= 0){
                cart.setQuantity(from.getQuantity());
            }
            if(from.getSelected() != null){
                cart.setProductSelected(from.getSelected());
            }

            opsForHash.put(redisKey,String.valueOf(productId),g.toJson(cart));

        return list(uid);
    }

    /**
     * 移除购物车某个商品
     * @param uid
     * @param productId
     * @return
     */
    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey,
                String.valueOf(productId));

        if(StringUtils.isEmpty(value)){
            //没有该商品,报错
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);//购物车无此商品
        }
        opsForHash.delete(redisKey,String.valueOf(productId));

        return list(uid);
    }

    //全选
    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash
                = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            //存入redis
            opsForHash.put(redisKey,String.valueOf(cart.getProductId()),
                    g.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unselectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash
                = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            //存入redis
            opsForHash.put(redisKey,String.valueOf(cart.getProductId()),
                    g.toJson(cart));
        }
        return list(uid);

    }

    ////获取购物中所有商品数量总和
    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    //公共的遍历方法
    public List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> opsForHash
                = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Cart cart = g.fromJson(entry.getValue(),Cart.class);
            cartList.add(cart);
        }
        return cartList;

    }
}
