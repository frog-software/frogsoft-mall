package org.frogsoft.mall.commodity.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByProductName(String name);

  ArrayList<Product> findAllBy();

}
