package com.imooc.sell.web;


import com.imooc.sell.consts.MallConsts;
import com.imooc.sell.form.CartAndForm;
import com.imooc.sell.form.CartUpdateFrom;
import com.imooc.sell.pojo.MallUser;
import com.imooc.sell.service.ICartService;
import com.imooc.sell.vo.CartVo;
import com.imooc.sell.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * created by Leo徐忠春
 * created Time 2020/2/7-17:42
 * email 1437665365@qq.com
 */
@RestController
@Slf4j
public class CartController {
    @Autowired
    private ICartService cartService;
    /**
     * 购物车List列表
     */
    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session){
        //从session里获取uid
        MallUser user = (MallUser) session.getAttribute
                (MallConsts.CURRENT);
        return cartService.list(user.getId());
    }


    /**
     * 购物车添加商品
     * @param
     * @return
     */
    @PostMapping("/carts")
    public ResponseVo<CartVo> add
            (@Valid @RequestBody CartAndForm form,
             HttpSession session){
        //从session里获取uid
        MallUser user = (MallUser) session.getAttribute
                (MallConsts.CURRENT);
        return cartService.add(user.getId(),form);
    }

    /**
     * 更新购物车
     */
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateFrom form,
                                     HttpSession session){
        //从session里获取uid
        MallUser user = (MallUser) session.getAttribute
                (MallConsts.CURRENT);
        return cartService.update(user.getId(),productId,form);
    }

    /**
     *移除购物车某个产品
     */
      @DeleteMapping("/carts/{productId}")
      public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                       HttpSession session){
          //从session里获取uid
          MallUser user = (MallUser) session.getAttribute
                  (MallConsts.CURRENT);
          return cartService.delete(user.getId(),productId);
      }

    /**
     * 全选中
     * @param session
     * @return
     */
    @PutMapping("/carts/selectAll")
      public ResponseVo<CartVo> selectAll(HttpSession session){
          //从session里获取uid
          MallUser user = (MallUser) session.getAttribute
                  (MallConsts.CURRENT);
          return cartService.selectAll(user.getId());
      }

    /*
     * 全不选中
     * @param session
     * @return
     */
    @PutMapping("/carts/unSelectAll")
      public ResponseVo<CartVo> unselectAll(HttpSession session){
          //从session里获取uid
          MallUser user = (MallUser) session.getAttribute
                  (MallConsts.CURRENT);
          return cartService.unselectAll(user.getId());
      }
    /*
     * 获取购物中所有商品数量总和
     * @param session
     * @return
     */
    @GetMapping("/carts/products/sum")
      public ResponseVo<Integer> sum(HttpSession session){
          //从session里获取uid
          MallUser user = (MallUser) session.getAttribute
                  (MallConsts.CURRENT);
        return cartService.sum(user.getId());
      }
}
