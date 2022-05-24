package org.frogsoft.mall.commodity.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByProductName(String name);

  ArrayList<Product> findAllByShop(Shop shop);

  ArrayList<Product> findAllByProductNameContains(String name_keyword);

  ArrayList<Product> findAllByBrandContains(String brand_keyword);

  ArrayList<Product> findAllByCategory(String category);

  ArrayList<Product> findAllByDescriptionContains(String description_keyword);

  ArrayList<Product> findAllBy();

}
