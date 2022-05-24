package org.frogsoft.mall.customer.repository;

import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.customer.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
