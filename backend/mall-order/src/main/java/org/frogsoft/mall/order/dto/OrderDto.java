package org.frogsoft.mall.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.customer.Address;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.order.controller.request.ProductPayingInfo;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDto {
    private Long id;
    private LocalDateTime orderTime;
    private String buyerName;
    private String sellerName;
    private BigDecimal totalPrice;
    private Set<OrderItemDto> products;
    private String remarks;
    private int status;
    private String logisticsNumber;
    private Address logisticsAddress;
}
