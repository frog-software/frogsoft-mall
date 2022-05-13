package org.frogsoft.mall.customer.dto;

import java.util.Set;
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
    private Set<CartItem> cartItems;
}
