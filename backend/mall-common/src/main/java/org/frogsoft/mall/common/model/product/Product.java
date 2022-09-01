package org.frogsoft.mall.common.model.product;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.util.JpaConverterListJson;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "products")
@Cacheable
@Cache(region = "product", usage = CacheConcurrencyStrategy.READ_WRITE )
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

    /* 因跨模块服务调用需要避免循环引用，故不再使用双向一对多关系
    @OneToMany(mappedBy = "product")
    private List<Comment> commentList ;*/

    // 存储缩略图的URL列表（以JSON格式存储字符串列表）
    @Convert(converter = JpaConverterListJson.class)
    private List<String> imageList;

    // 存储头图的URL
    private String thumb;

    /**
     * 多对一关系，由多方维系关系
     * 通过products表的shop_id字段和shops表的id主键字段做关系映射
     */
    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

}