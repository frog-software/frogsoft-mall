package org.frogsoft.mall.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.model.customer.Address;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.order.Order;
import org.frogsoft.mall.common.model.order.OrderItem;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.order.controller.client.CustomerClient;
import org.frogsoft.mall.order.controller.client.ProductClient;
import org.frogsoft.mall.order.controller.client.ShopClient;
import org.frogsoft.mall.order.controller.request.AddOrderRequest;
import org.frogsoft.mall.order.dto.OrderDto;
import org.frogsoft.mall.order.dto.OrderDtoMapper;
import org.frogsoft.mall.order.dto.OrderItemDtoMapper;
import org.frogsoft.mall.order.repository.AddressRepository;
import org.frogsoft.mall.order.repository.OrderItemRepository;
import org.frogsoft.mall.order.repository.OrderRepository;
import org.frogsoft.mall.order.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    // 索引数据库仓库持久类
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;

    // 打包Dto类
    private final OrderDtoMapper orderDtoMapper;
    private final OrderItemDtoMapper orderItemDtoMapper;


    // 跨模块调用访问其他模块的服务类
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final ShopClient shopClient;

    /*带有“client”的，为后端专属服务调用，直接返回相应model类型*/

    /*提供给本模块Controller的服务*/

    // 顾客提交订单（无支付信息、物流单号信息）
    public OrderDto saveOrder(AddOrderRequest addOrderRequest){

        // 初始化订单买家和地址
        Customer buyer = customerClient.getCustomerByUsername(addOrderRequest.getCustomerName());
        Address address = addressRepository.getById(addOrderRequest.getLogisticsAddressID());

        // 初始化订单商店
        Shop seller = shopClient.getShop(addOrderRequest.getShopId());

        // 初始化订单商品列表
        Set<OrderItem> postOrderItems = new HashSet<>();
        // 初始化总价格
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (var itemPost: addOrderRequest.getOrderProducts()
        ) {
            // 获得商品对象
            Product productInOrderItem = productClient.getProduct(itemPost.getProductID());
            if(!Objects.equals(productInOrderItem.getShop().getId(), seller.getId()))
                throw new UnsupportedOperationException("商店id和订单内商品不匹配！"); // TODO：改为自定义异常抛出
            // 保存订单商品对象到列表
            OrderItem newOrderItem = orderItemRepository.save(new OrderItem()
                .setProduct(productInOrderItem)
                .setAmount(itemPost.getAmount())
                .setRemarks(itemPost.getRemarks()));
            postOrderItems.add(newOrderItem);
            // 更新总价格
            totalPrice = totalPrice.add(productInOrderItem.getPrice().multiply(BigDecimal.valueOf(itemPost.getAmount())));
        }

        // 保存订单对象
        Order newOrder = orderRepository.save(new Order()
            .setBuyer(buyer)
            .setSeller(seller)
            .setRemarks(addOrderRequest.getRemarks())
            .setOrderTime(LocalDateTime.now())
            .setStatus(1) // 1：已提交 2：已支付 3：已收货 4：已取消
            .setLogisticsAddress(address)
            .setOrderItems(postOrderItems)
            .setTotalPrice(totalPrice));

        return orderDtoMapper.toOrderDto(newOrder);
    }

    public OrderDto editOrderCustomer(AddOrderRequest addOrderRequest){
        return new OrderDto();
    }


}
