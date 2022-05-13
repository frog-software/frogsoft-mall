package org.frogsoft.mall.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.forbidden.ForbiddenException;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.exception.user.UserNotFoundException;
import org.frogsoft.mall.common.model.customer.Address;
import org.frogsoft.mall.common.model.customer.BankCard;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.order.Order;
import org.frogsoft.mall.common.model.order.OrderItem;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.order.controller.client.CustomerClient;
import org.frogsoft.mall.order.controller.client.ProductClient;
import org.frogsoft.mall.order.controller.client.ShopClient;
import org.frogsoft.mall.order.controller.request.AddOrderRequest;
import org.frogsoft.mall.order.controller.request.EditOrderRequestSeller;
import org.frogsoft.mall.order.controller.request.OrderLogInfoRequest;
import org.frogsoft.mall.order.controller.request.OrderPayingRequest;
import org.frogsoft.mall.order.dto.OrderDto;
import org.frogsoft.mall.order.dto.OrderDtoMapper;
import org.frogsoft.mall.order.dto.OrderItemDtoMapper;
import org.frogsoft.mall.order.repository.AddressRepository;
import org.frogsoft.mall.order.repository.BankCardRepository;
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
    private final BankCardRepository bankCardRepository;
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
                throw new ForbiddenException("商店id和订单内商品不匹配！"); // TODO：改为自定义异常抛出
            // 保存订单商品对象到列表
            OrderItem newOrderItem = orderItemRepository.save(new OrderItem()
                .setProduct(productInOrderItem)
                .setAmount(itemPost.getAmount())
                .setRemarks(itemPost.getRemarks())
                .setOrderPrice(productInOrderItem.getPrice()) // 订单内商品价格设置为商品本身的价格
            );
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
            .setStatus(1) // 1：已提交 2：已支付 3：已发货 4：已收获 -1：已取消
            .setLogisticsAddress(address)
            .setOrderItems(postOrderItems)
            .setTotalPrice(totalPrice));

        return orderDtoMapper.toOrderDto(newOrder);
    }

    // 顾客修改订单（仍使用AddOrderRequest，但仅允许修改地址、订单备注）
    public OrderDto editOrderCustomer(Long id, AddOrderRequest addOrderRequest){
        Order currOrder = orderRepository.findById(id)
            .orElseThrow(()-> new NotFoundException("Order not found."));

        if(currOrder.getStatus() >= 3){
            throw new ForbiddenException("订单已发货，无法修改！");
        }

        Address address = addressRepository.getById(addOrderRequest.getLogisticsAddressID());
        Order newOrder = orderRepository.save(currOrder
            .setRemarks(addOrderRequest.getRemarks())
            .setLogisticsAddress(address));

        return orderDtoMapper.toOrderDto(newOrder);
    }

    // 商家修改订单（仍使用AddOrderRequest，仅允许修改商品信息）
    public OrderDto editOrderSeller(Long id, EditOrderRequestSeller editOrderRequestSeller, UserDetail authenticatedUser){
        Order currOrder = orderRepository.findById(id)
            .orElseThrow(()-> new NotFoundException("Order not found."));
        Shop seller = currOrder.getSeller();
        User authSeller = userRepository.findByUsername(authenticatedUser.getUsername())
            .orElseThrow(() -> new UserNotFoundException(authenticatedUser.getUsername()));

        if(!Objects.equals(seller.getOwner().getId(), authSeller.getId())){
            throw new ForbiddenException("卖家用户id与订单内商店id不匹配！"); // TODO：改为自定义异常抛出
        }
        if(currOrder.getStatus() >= 2){
            throw new ForbiddenException("订单已支付，无法修改！");
        }

        // 获得订单商品列表
        Set<OrderItem> currOrderItems = currOrder.getOrderItems();

        for (var itemToEdit: editOrderRequestSeller.getOrderProducts()
        ) {
            boolean isFoundInCurrList = false;
            // 获得传入的商品对象
            Product productInEditOrderItem = productClient.getProduct(itemToEdit.getProductID());
            if(!Objects.equals(productInEditOrderItem.getShop().getId(), seller.getId()))
                throw new ForbiddenException("商店id和订单内商品不匹配！"); // TODO：改为自定义异常抛出

            // 查找该商品是否已经在订单中
            for (var orderItem: currOrderItems
            ) {
                // 如果已经在，直接修改信息
                if(orderItem.getProduct().equals(productInEditOrderItem)){
                    OrderItem newOrderItem = orderItemRepository.save(orderItem
                        .setAmount(itemToEdit.getAmount())
                        .setOrderPrice(itemToEdit.getOrderPrice()));
                    isFoundInCurrList = true;
                }
            }
            if(!isFoundInCurrList){
                // 否则，新增商品
                OrderItem newOrderItem = orderItemRepository.save(new OrderItem()
                    .setProduct(productInEditOrderItem)
                    .setAmount(itemToEdit.getAmount())
                    .setOrderPrice(itemToEdit.getOrderPrice()));
                currOrderItems.add(newOrderItem);
            }

        }

        // 重新计算总价格
        BigDecimal newTotalPrice = BigDecimal.ZERO;
        for (var item: currOrderItems){
            // 更新总价格
            newTotalPrice = newTotalPrice.add(item.getOrderPrice().multiply(BigDecimal.valueOf(item.getAmount())));
        }

        // 保存Order对象
        Order newOrder = orderRepository.save(currOrder
            .setRemarks(editOrderRequestSeller.getRemarks())
            .setStatus(1)
            .setOrderItems(currOrderItems)
            .setTotalPrice(newTotalPrice));

        return orderDtoMapper.toOrderDto(newOrder);
    }

    // 顾客支付订单（在支付成功后，上传支付信息）
    public OrderDto editOrderPaymentCustomer(Long id, OrderPayingRequest orderPayingRequest){
        Order currOrder = orderRepository.findById(id)
            .orElseThrow(()-> new NotFoundException("Order not found."));

        if(currOrder.getStatus() != 1){
            throw new ForbiddenException("当前订单不可支付！");
        }

        Order newOrder = new Order();

        if(orderPayingRequest.getBankCardID() != null){
           BankCard payingCard = bankCardRepository.getById(orderPayingRequest.getBankCardID());
            newOrder = orderRepository.save(currOrder
                .setStatus(2) // 已支付
                .setPayment(payingCard.getBankcardInc())
                .setAccount(payingCard.getBankcardNum())
            );
        }
        else{
            // 其他方式支付，仅写入支付信息
            newOrder = orderRepository.save(currOrder
                .setStatus(2) // 已支付
                .setPayment(orderPayingRequest.getPayment())
            );
        }

        return orderDtoMapper.toOrderDto(newOrder);
    }

    // 商家修改订单（上传物流单号）
    public OrderDto editOrderLogisticsSeller(Long id, OrderLogInfoRequest orderLogInfoRequest){
        Order currOrder = orderRepository.findById(id)
            .orElseThrow(()-> new NotFoundException("Order not found."));

        if(currOrder.getStatus() != 2){
            throw new ForbiddenException("当前订单还不能上传物流信息！");
        }
        Order newOrder = orderRepository.save(currOrder
            .setStatus(3) // 已发货
            .setLogisticsNumber(orderLogInfoRequest.getLogisticsNumber())
        );

        return orderDtoMapper.toOrderDto(newOrder);
    }

    // 顾客确认收货
    public OrderDto editOrderReceivingCustomer(Long id){
        Order currOrder = orderRepository.findById(id)
            .orElseThrow(()-> new NotFoundException("Order not found."));

        if(currOrder.getStatus() != 3){
            throw new ForbiddenException("当前订单未发货！");
        }
        Order newOrder = orderRepository.save(currOrder
            .setStatus(4) // 已收货
        );
        return orderDtoMapper.toOrderDto(newOrder);
    }

    // 商家取消订单：将订单标记为已取消，而不会实际删除订单
    public OrderDto closeOrderSeller(Long id, UserDetail authenticatedUser){

        Order currOrder = orderRepository.findById(id)
            .orElseThrow(()-> new NotFoundException("Order not found."));

        /*if(!Objects.equals(currOrder.getSeller().getOwner().getId(), authenticatedUser.getId())){
            throw new ForbiddenException("卖家用户id与订单内商店id不匹配！");
        }*/

        if(currOrder.getStatus() >= 3){
            throw new ForbiddenException("当前订单已发货，无法取消！");
        }
        Order newOrder = orderRepository.save(currOrder
            .setStatus(-1) // 已取消
        );
        return orderDtoMapper.toOrderDto(newOrder);
    }


    // 顾客删除订单：对已下单但未付款的订单、以及已收货，不存在售后问题的订单，允许删除订单
    public void deleteOrderCustomer(Long id, UserDetail authenticatedUser){
        Order currOrder = orderRepository.findById(id)
            .orElseThrow(()-> new NotFoundException("Order not found."));

        if(!Objects.equals(currOrder.getBuyer().getUser().getUsername(), authenticatedUser.getUsername())){
            throw new ForbiddenException("只能取消自己创建的订单！");
        }

        Order savedOrder = orderRepository.save(currOrder
                .setBuyer(null)
                .setSeller(null)
                .setLogisticsAddress(null)
                .setStatus(-1) // 已取消
        );
        orderRepository.delete(savedOrder);
    }

    public OrderDto getSingleOrder(Long id){
        return orderDtoMapper.toOrderDto(
            orderRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Order not found."))
        );

    }

    public ArrayList<OrderDto> getAllOrderCustomer(UserDetail authenticatedUser){
        Customer buyer = customerClient.getCustomerByUsername(authenticatedUser.getUsername());

        return orderRepository
            .findAllByBuyer(buyer)
            .stream()
            .map(orderDtoMapper::toOrderDto)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<OrderDto> getAllOrderSeller(UserDetail authenticatedUser) {
        User authSeller = userRepository.findByUsername(authenticatedUser.getUsername())
            .orElseThrow(() -> new UserNotFoundException(authenticatedUser.getUsername()));
        ArrayList<Shop> shops = shopClient.getAllShopByOwner(authSeller.getId());

        ArrayList<OrderDto> result = new ArrayList<>();

        for (var shop: shops
        ) {
            result.addAll(orderRepository
                .findAllBySeller(shop)
                .stream()
                .map(orderDtoMapper::toOrderDto)
                .collect(Collectors.toCollection(ArrayList::new))
            );
        }

        return result;
    }
}
