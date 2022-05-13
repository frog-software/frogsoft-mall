package org.frogsoft.mall.order.repository;

import java.util.List;
import org.frogsoft.mall.common.model.order.OrderItem;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByProduct(Product product);
}
