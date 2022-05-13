package org.frogsoft.mall.order.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemInfoSeller implements Serializable {
    private Long productID;
    private int amount;
    private BigDecimal orderPrice; // 商家可能修改订单商品的价格
}
