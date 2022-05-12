package org.frogsoft.mall.order.repository;

import org.frogsoft.mall.common.model.customer.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
