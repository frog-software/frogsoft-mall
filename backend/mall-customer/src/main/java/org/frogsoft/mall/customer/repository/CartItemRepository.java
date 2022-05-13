package org.frogsoft.mall.customer.repository;

import org.frogsoft.mall.common.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

}
