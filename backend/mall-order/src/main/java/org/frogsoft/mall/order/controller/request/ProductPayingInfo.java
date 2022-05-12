package org.frogsoft.mall.order.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductPayingInfo implements Serializable {
    private Long productID;
    private String productName;
    private int amount;
    private String remarks;
    // private BigDecimal orderPrice; // 前端下单时无需提交单价
}
