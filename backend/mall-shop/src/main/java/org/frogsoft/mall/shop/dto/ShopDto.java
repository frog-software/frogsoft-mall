package org.frogsoft.mall.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ShopDto {
    private String shopName;
    private float rate;
    private int tradeQuantity;
    private String ownerName;
    private String shopImage;
}
