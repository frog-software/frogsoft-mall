package org.frogsoft.mall.order.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.order.Order;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    ArrayList<Order> findAllBy();

    ArrayList<Order> findAllByBuyer(Customer buyer);

    ArrayList<Order> findAllBySeller(User seller);


}
