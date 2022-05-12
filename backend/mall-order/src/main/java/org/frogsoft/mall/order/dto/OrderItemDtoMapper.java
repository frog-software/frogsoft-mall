package org.frogsoft.mall.order.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.order.OrderItem;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderItemDtoMapper {
    public OrderItemDto toOrderItemDto(OrderItem orderItem) {

        return new OrderItemDto()
            .setAmount(orderItem.getAmount())
            .setOrderPrice(orderItem.getOrderPrice())
            .setRemarks(orderItem.getRemarks());
    }
}
