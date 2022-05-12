package org.frogsoft.mall.customer.repository;

import java.util.ArrayList;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.frogsoft.mall.common.model.customer.Customer;


public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUser(User user);

    ArrayList<Customer> findAllBy();
}
