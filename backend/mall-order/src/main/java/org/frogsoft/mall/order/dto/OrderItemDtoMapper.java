package org.frogsoft.mall.order.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.order.OrderItem;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.order.controller.client.ProductClient;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderItemDtoMapper {


    public OrderItemDto toOrderItemDto(OrderItem orderItem) {

        Product product = orderItem.getProduct();

        return new OrderItemDto()
            .setId(product.getId()) // 注意是商品id
            .setAmount(orderItem.getAmount())
            .setOrderPrice(orderItem.getOrderPrice())
            .setRemarks(orderItem.getRemarks())
            .setProductName(product.getProductName())
            .setThumb(product.getThumb());
    }
}
