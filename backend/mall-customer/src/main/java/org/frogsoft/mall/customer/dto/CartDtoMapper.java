package org.frogsoft.mall.customer.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.stereotype.Component;

import java.util.AbstractSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class CartDtoMapper {
    public CartDto toCartDto(Customer customer){
        User user = customer.getUser();
        if(user==null){
            throw new NotFoundException("User not found.");
        }
        return new CartDto()
            .setUsername(user.getUsername())
            .setCartItems(customer.getCartItemList())
            .setItemCount(customer.getCartItemList().size());
    }


    public CartDto toCartDto(CartItem cartItem){
        Set<CartItem> cartItems = null;
        cartItems.add(cartItem);
        return new CartDto()
                .setUsername(cartItem.getUsername())
                .setCartItems(cartItems)
                .setItemCount(cartItem.getAmount());
    }
}
