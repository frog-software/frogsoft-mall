package org.frogsoft.mall.commodity.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.parameters.P;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  Optional<Product> findByProductName(String name);

  ArrayList<Product> findAllByShop(Shop shop);

  ArrayList<Product> findAllByProductNameContains(String name_keyword);

  ArrayList<Product> findAllByBrandContains(String brand_keyword);

  ArrayList<Product> findAllByCategory(String category);

  ArrayList<Product> findAllByDescriptionContains(String description_keyword);

  ArrayList<Product> findAllBy();

  Page<Product> findAllBy(Pageable pageable);

}
