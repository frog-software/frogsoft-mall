package org.frogsoft.mall.shop.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.shop.Shop;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShopDtoMapper {
    public ShopDto toShopDto(Shop shop){
        return new ShopDto()
            .setShopName(shop.getShopName())
            .setRate(shop.getRate())
            .setTradeQuantity(shop.getTradeQuantity())
            .setOwnerName("Null") // TODO：接入用户模块
            .setShopImage(shop.getShopImage());

    }
}
