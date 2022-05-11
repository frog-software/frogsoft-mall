package org.frogsoft.mall.user.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUser(User user);

    ArrayList<Customer> findAllBy();

}