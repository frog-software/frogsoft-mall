package org.frogsoft.mall.commodity.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.shop.Shop;

@Getter
@Setter
@Accessors(chain = true)
public class ProductDto {
  private Long productId;
  private String productName;
  private String category;
  private String brand;
  private String description;
  private ShopDto shop; // 商品对应的商店信息
  private BigDecimal price;
  private String thumb; // 缩略图，默认返回imageList的第一个
  private List<String> imageList;
}
