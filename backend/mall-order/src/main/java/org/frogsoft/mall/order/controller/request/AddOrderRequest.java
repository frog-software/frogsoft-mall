package org.frogsoft.mall.order.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class AddOrderRequest implements Serializable {

    private String customerName;
    private Long shopId;
    private String remarks;
    private Set<ProductPayingInfo> orderProducts;
    private Long logisticsAddressID;
}

