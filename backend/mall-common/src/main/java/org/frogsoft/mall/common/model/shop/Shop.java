package org.frogsoft.mall.common.model.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
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

    private String shopImage;

    /**
     * 单向一对一外键
     * 由shops表持有owner_id，保存users表的id
     * referencedColumnName是指owner_id指定的users的映射字段，默认是id主键
     */
    @OneToOne
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    private User owner;

    //  * 因跨模块服务调用需要避免循环引用，故不再使用双向一对多关系
    // @OneToMany(mappedBy = "shop")
    // private List<Product> productList;
}
