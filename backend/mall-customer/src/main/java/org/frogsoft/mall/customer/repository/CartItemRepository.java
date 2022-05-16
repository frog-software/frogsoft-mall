package org.frogsoft.mall.customer.repository;

import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    ArrayList<CartItem> findAllBy();

    ArrayList<CartItem> findAllByOwner_Id(Long owner_id);

    ArrayList<CartItem> findAllByUsername(String username);

    Optional<CartItem> findByUsername(String username);
}
