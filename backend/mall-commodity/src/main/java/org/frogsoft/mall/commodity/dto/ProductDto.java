package org.frogsoft.mall.commodity.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ProductDto {

  private String productName;
  private String category;
  private String brand;
  // private String shopName;
  private float prize;
}
