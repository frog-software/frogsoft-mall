package org.frogsoft.mall.commodity.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShopDtoMapper {
    public ShopDto toShopDto(Shop shop){

        User owner = shop.getOwner();
        String ownerName = "null";
        if (owner != null)
            ownerName = owner.getUsername();

        return new ShopDto()
            .setId(shop.getId())
            .setShopName(shop.getShopName())
            .setRate(shop.getRate())
            .setTradeQuantity(shop.getTradeQuantity())
            .setOwnerName(ownerName)
            .setShopImage(shop.getShopImage());

    }
}
