package org.frogsoft.mall.common.model.comment;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "comments")
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int type;

    private Long parentId;

    private LocalDateTime commentTime;

    // 一对一外键：评论的顾客用户
    @OneToOne
    @JoinColumn(name="customer_id",referencedColumnName = "id")
    private User customer;

    // 多对一外键：评论对应的商品
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
