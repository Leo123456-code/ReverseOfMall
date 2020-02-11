package com.imooc.sell.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.sell.dao.OrderItemMapper;
import com.imooc.sell.dao.OrderMapper;
import com.imooc.sell.dao.ProductMapper;
import com.imooc.sell.dao.ShippingMapper;
import com.imooc.sell.enums.PaymentTypeEnum;
import com.imooc.sell.pojo.*;
import com.imooc.sell.service.ICartService;
import com.imooc.sell.service.IOrderService;
import com.imooc.sell.vo.OrderItemVo;
import com.imooc.sell.vo.OrderVo;
import com.imooc.sell.vo.ResponseVo;
import com.imooc.sell.vo.ShippingVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.imooc.sell.enums.OrderStatusEnum.*;
import static com.imooc.sell.enums.ProductStatusEnum.ON_SALE;
import static com.imooc.sell.enums.ResponseEnum.*;

/**
 * created by Leo徐忠春
 * created Time 2020/2/9-17:01
 * email 1437665365@qq.com
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ICartService cartService;


    /**
     * 创建订单
     * @param uid
     * @param shippingId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo<OrderVo> create(Integer shippingId,Integer uid) throws Exception{
        //收货地址效验
        //查收货地址
        Shipping shipping =
                shippingMapper.selectByUidAndShippingId(shippingId,uid);
        ShippingVo shippingVo = new ShippingVo();
        BeanUtils.copyProperties(shipping,shippingVo);
        log.info("shippingVo={}",shippingVo);
        if(shipping==null){
            return ResponseVo.error(SHIPPING_NOT_EXIST);
        }
        //获取购物车,效验购物车(是否有商品,库存)
        List<Cart> cartList = cartService.listForCart(uid).stream().
                filter(Cart::getProductSelected).collect(Collectors.toList());
        log.info("result={}",cartList);
        //判断购物车是否有商品
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(CART_SELECTED_IS_EMPTY);
        }
        //优化
        //获取cartList里的productIds
        Set<Integer> prodectIdSet =
                cartList.stream().map(Cart::getProductId).
                        collect(Collectors.toSet());
        List<Product> productList =
                productMapper.selectByProductIdSet(prodectIdSet);

        Map<Integer, Product> map = productList.stream().collect
                (Collectors.toMap(Product::getId, product -> product));
        Long orderNO = generateOrderNo();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Cart cart : cartList) {
            //根据productId查数据库
            Product product = map.get(cart.getProductId());
            //是否有商品
            if(product==null){
                return ResponseVo.error(PRODUCT_NOT_EXIST,
                        "商品不存在,productId="+cart.getProductId());
            }
            //判断商品上下架状态
            if(!ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE,
                        "商品不是在售状态:"+product.getName());
            }
            //库存是否充足
            if(product.getStock() < cart.getQuantity()){
                return ResponseVo.error(CART_STOCK_IS_EMPTY,
                        "库存不足:"+product.getName());
            }
            OrderItem orderItem = buildOrderItem(uid, orderNO, cart.getQuantity(),
                    product);
            orderItemList.add(orderItem);

            //减库存
            product.setStock(product.getStock()-cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if(row <= 0){
                return ResponseVo.error(ERROR);
            }
        }


        //计算总价(只计算被选中的商品)
        //生成订单,入库 order 和 orderItem表,事务控制
        Order order = buildOrder(uid, orderNO, shippingId, orderItemList);
        //写入order
        int rows = orderMapper.insertSelective(order);
        if(rows<=0){
            return ResponseVo.error(ERROR);
        }
        log.info("orderItemList={}",gson.toJson(orderItemList));
        //写入orderItem
        int rowOrderItem = orderItemMapper.batchInsertSelective(orderItemList);
        if(rowOrderItem<=0){
            return ResponseVo.error(ERROR);
        }
        //更新购物车(删除选中的商品),redis有事务
        //遍历购物车
        for (Cart cart : cartList) {
            cartService.delete(uid,cart.getProductId());
        }
        //构造orderVo,返回给前端
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);

        return ResponseVo.success(orderVo);
    }

    /**
     * 分页查询订单列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList =
                orderItemMapper.selectByOrderNoSet(orderNoSet);
        //list 转 map
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream().collect(Collectors.groupingBy
                (OrderItem::getOrderNo));


        Set<Integer> shippingIdSet = orderList.stream().map(Order::getShippingId).
                collect(Collectors.toSet());

        List<OrderVo> orderVoList = new ArrayList<>();
        List<Shipping> shippingList =
                shippingMapper.selectByIdSet(shippingIdSet);
        Map<Integer, Shipping> shippingMap = shippingList.stream().collect(Collectors.toMap
                (Shipping::getId, shipping -> shipping));
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order, orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }

        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderVoList);
        pageInfo.setList(orderVoList);

        return ResponseVo.success(pageInfo);
    }

    /**
     * 订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null || !order.getUserId().equals(uid)){
            return ResponseVo.error(CART_NO);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList =
                orderItemMapper.selectByOrderNoSet(orderNoSet);
        Shipping shipping =
                shippingMapper.selectByPrimaryKey(order.getShippingId());
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);

        return ResponseVo.success(orderVo);
    }

    /**
     * 订单取消
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            return ResponseVo.error(CART_NO);
        }
        //只有未付款的状态,才可以取消
        if(!order.getStatus().equals(NO_PAY.getCode())){
            return ResponseVo.error(ORDER_STATUS_ERROR);//订单状态有误
        }
        order.setStatus(CANCELED.getCode());//订单状态改为已取消
        order.setCloseTime(new Date());
        int rows = orderMapper.updateByPrimaryKeySelective(order);
        if(rows<=0){
            return ResponseVo.error(ERROR);
        }

        return ResponseVo.success();
    }

    //已支付
    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null){
            throw new RuntimeException(CART_NO.getMessage()+"订单id"+order.getOrderNo());
        }
        //只有未付款的订单才可以变成已付款
        if(!order.getStatus().equals(NO_PAY.getCode())){
            throw new RuntimeException(ORDER_STATUS_ERROR.getMessage()
                    +"订单id"+order.getOrderNo());//订单状态有误
        }

        order.setStatus(PAID.getCode());//订单状态改为已支付
        order.setPaymentTime(new Date());//支付时间
        order.setSendTime(new Date());
        order.setUpdateTime(new Date());
        log.info("------order:{}",gson.toJson(order));
        int rows = orderMapper.updateByPrimaryKeySelective(order);
        if(rows<=0){
            throw new RuntimeException(ERROR.getMessage()+"" +
                    "支付失败,订单id"+order.getOrderNo());
        }


    }

    //构造orderVo
    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {

        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order,orderVo);

        List<OrderItemVo> orderItemVoList =new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(orderItem,orderItemVo);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);

        orderVo.setShippingId(shipping.getId());
        orderVo.setShippingVo(shipping);

        return orderVo;
    }

    ////构造Order
    private Order buildOrder(Integer uid,Long orderNo,Integer shippingId,
                             List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        //累加值：实际付款
        BigDecimal payment = orderItemList.stream().map(OrderItem::getTotalPrice).
                reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setPayment(payment);//实际付款
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(NO_PAY.getCode());
        order.setPaymentTime(new Date());
        order.setCreateTime(new Date());
        order.setCloseTime(new Date());
        order.setEndTime(new Date());
        return order;
    }

    //生成订单号
    //企业级:分布式唯一 id
    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(9999);
    }

    //构造OrderItem
    private OrderItem buildOrderItem(Integer uid,Long orderNo,Integer quantity,Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setUserId(uid);
        orderItem.setOrderNo(orderNo);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        //总价 = 单价 * 数量
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        return orderItem;
    }
}
