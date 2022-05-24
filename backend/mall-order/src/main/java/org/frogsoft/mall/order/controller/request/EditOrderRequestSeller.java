package org.frogsoft.mall.order.controller.request;

import java.io.Serializable;
import java.util.Set;
import lombok.Data;

@Data // 商家修改订单时提交的信息
public class EditOrderRequestSeller implements Serializable {
    private Long shopId;
    private String remarks;
    private Set<OrderItemInfoSeller> orderProducts;
}

