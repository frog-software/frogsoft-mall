package org.frogsoft.mall.order.controller.request;

import java.io.Serializable;
import java.util.Set;
import lombok.Data;

@Data
public class OrderPayingRequest implements Serializable {
    private String payment; // 支付方式（留后用）
    private Long bankCardID;
}
