package org.frogsoft.mall.common.model.product;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.comment.Comment;
import org.frogsoft.mall.common.model.shop.Shop;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "products")
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String description;

    private String category;

    private String brand;

    // 高精度数值类型
    private BigDecimal price;
    // private float price;

    // 一对多关系：评论模型外键
    @OneToMany(mappedBy = "product")
    private List<Comment> commentList ;

    /**
     * 多对一关系，由多方维系关系
     * 通过products表的shop_id字段和shops表的id主键字段做关系映射
     */
    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

}