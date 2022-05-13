package org.frogsoft.mall.customer.controller.request;

import java.io.Serializable;
import lombok.Data;

@Data
public class AddCartItemRequset implements Serializable {
    private Long productID;
    private int amount;
    private String remarks;
}
