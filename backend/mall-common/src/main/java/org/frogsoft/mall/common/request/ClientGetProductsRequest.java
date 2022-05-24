package org.frogsoft.mall.common.request;
import java.io.Serializable;

import lombok.Data;
import org.frogsoft.mall.common.model.shop.Shop;

@Data
public class ClientGetProductsRequest implements Serializable {
    private Shop targetShop;
}
