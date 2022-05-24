package org.frogsoft.mall.order.dto;

import java.math.BigDecimal;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.product.Product;

@Getter
@Setter
@Accessors(chain = true)
public class OrderItemDto {
    private Long id; // 商品id
    private int amount;
    private BigDecimal orderPrice; // 商品在本订单上的价格(单价)
    private String remarks;
    private String productName; // 商品名称
    private String thumb; // 商品缩略图（冗余）
}
