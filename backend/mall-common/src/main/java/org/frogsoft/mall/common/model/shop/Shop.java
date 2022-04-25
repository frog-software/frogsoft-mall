package org.frogsoft.mall.common.model.shop;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.user.User;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "shops")
@ToString
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shopName;

    private float rate;

    private int tradeQuantity;

    /**
     * 单向一对一外键
     * 由shops表持有owner_id，保存users表的id
     * referencedColumnName是指owner_id指定的users的映射字段，默认是id主键
     */
    @OneToOne
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    private User owner;

    /**
     * OneToMany和ManyToOne配合使用时，由ManyToOne多方进行关系管理
     * mappedBy属性是Many方的属性名称
     */
    @OneToMany(mappedBy = "shop")
    private List<Product> productList;
}
