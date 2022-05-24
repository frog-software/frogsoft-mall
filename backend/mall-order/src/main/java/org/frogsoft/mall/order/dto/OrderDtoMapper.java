package org.frogsoft.mall.order.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.order.Order;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderDtoMapper {

    public final OrderItemDtoMapper orderItemDtoMapper;
    public final AddressDtoMapper addressDtoMapper;

    public OrderDto toOrderDto(Order order){

        return new OrderDto()
            .setId(order.getId())
            .setOrderTime(order.getOrderTime())
            .setBuyerName(order.getBuyer().getUser().getUsername())
            .setSellerName(order.getSeller().getShopName())
            .setRemarks(order.getRemarks())
            .setStatus(order.getStatus())
            .setTotalPrice(order.getTotalPrice())
            .setPayment(order.getPayment())
            .setLogisticsNumber(order.getLogisticsNumber())
            .setLogisticsAddress(addressDtoMapper.toAddressDto(order.getLogisticsAddress()))
            .setProducts(order.getOrderItems()
                .stream()
                .map(orderItemDtoMapper::toOrderItemDto)
                .collect(Collectors.toSet()));
    }
}
