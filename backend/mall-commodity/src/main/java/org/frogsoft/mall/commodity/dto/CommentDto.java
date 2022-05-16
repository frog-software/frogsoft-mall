package org.frogsoft.mall.commodity.dto;

import java.time.LocalDateTime;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.product.Product;

@Getter
@Setter
@Accessors(chain = true)
// comment details
public class CommentDto {
    public Long commentId;
    public float type;
    public String content;
    public LocalDateTime commentTime;

    public Long customerId;
    public ProductDto product;
    public Long parentId;
}
