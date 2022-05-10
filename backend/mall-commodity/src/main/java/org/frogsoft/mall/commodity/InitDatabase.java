package org.frogsoft.mall.commodity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import org.frogsoft.mall.commodity.repository.ProductRepository;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InitDatabase {

  @Bean
  CommandLineRunner init(
      ProductRepository productRepository
  ) {
    return args -> {
      // 存储一个测试商品
      Optional<Product> product = productRepository.findByProductName("test_product");
      if (product.isEmpty()) {
        productRepository.save(new Product()
            .setProductName("test_product")
            .setBrand("Frogsoft")
            .setCategory("Food")
            .setDescription("你是一个一个一个商品啊")
            .setPrice(BigDecimal.ONE)
        );
      }

    };
  }
}
