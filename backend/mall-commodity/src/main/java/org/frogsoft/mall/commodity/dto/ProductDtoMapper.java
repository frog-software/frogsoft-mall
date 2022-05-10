package org.frogsoft.mall.commodity.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductDtoMapper {

  public ProductDto toProductDto(Product product) {

    return new ProductDto()
        .setProductId(product.getId())
        .setProductName(product.getProductName())
        .setBrand(product.getBrand())
        .setCategory(product.getCategory())
        .setPrice(product.getPrice())
        .setShopName(product.getShop().getShopName())
        .setDescription(product.getDescription());
  }
}
