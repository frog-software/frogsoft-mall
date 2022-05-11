package org.frogsoft.mall.shop.controller.request;

import java.io.Serializable;
import lombok.Data;

@Data
public class AddShopRequset implements Serializable {
    private String shopName;
    private String shopImage;
}
