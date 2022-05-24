package org.frogsoft.mall.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.customer.Address;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDto {
    private Long id; // 订单id
    private LocalDateTime orderTime;
    private String buyerName;
    private String sellerName;
    private BigDecimal totalPrice;
    private Set<OrderItemDto> products;
    private String remarks;
    private String payment; // 输出订单信息时，只输出支付方式，从而保护银行卡号等隐私信息
    private int status;
    private String logisticsNumber;
    private AddressDto logisticsAddress;
}
