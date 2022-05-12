package org.frogsoft.mall.customer.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.cart.CartItem;

@Getter
@Setter
@Accessors(chain = true)
public class CartDto {
    private String username;
    private int itemCount;
    private List<CartItem> cartItems;
}
