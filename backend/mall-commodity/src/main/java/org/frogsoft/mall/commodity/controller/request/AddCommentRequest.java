package org.frogsoft.mall.commodity.controller.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class AddCommentRequest implements Serializable {
    // private Long productId; 无需prodcutID，因为在path var中已经传入评论对应商品的id
    private String content;
    private float type;
    private Long parentId;
}

