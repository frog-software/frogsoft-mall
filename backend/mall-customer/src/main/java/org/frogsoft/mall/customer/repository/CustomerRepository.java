package org.frogsoft.mall.customer.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findById(Long id);

    ArrayList<Customer> findAllBy();

}
