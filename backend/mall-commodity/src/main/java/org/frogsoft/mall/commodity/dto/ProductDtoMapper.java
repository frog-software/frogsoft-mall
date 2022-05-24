package org.frogsoft.mall.commodity.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.product.Product;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusPushGatewayManager.ShutdownOperation;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductDtoMapper {

  private final ShopDtoMapper shopDtoMapper;
  public ProductDto toProductDto(Product product) {

    return new ProductDto()
        .setProductId(product.getId())
        .setProductName(product.getProductName())
        .setBrand(product.getBrand())
        .setCategory(product.getCategory())
        .setPrice(product.getPrice())
        .setShop(shopDtoMapper.toShopDto(product.getShop()))
        .setDescription(product.getDescription())
        .setThumb(product.getThumb())
        .setImageList(product.getImageList());
  }
}
