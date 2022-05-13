package org.frogsoft.mall.common.model.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.frogsoft.mall.common.model.customer.Address;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "orders")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderTime;

    private BigDecimal totalPrice;

    private String remarks;

    private String payment;

    private String account;

    // 1：已提交 2：已支付 3：已发货 4：已收货 -1：已取消
    private int status;

    private String logisticsNumber;

    @OneToOne
    @JoinColumn(name="address_id", referencedColumnName = "id")
    private Address logisticsAddress;

    // 卖家：订单涉及商品所属的商店（注意：同一订单只能包含同一商店的商品）
    @OneToOne
    @JoinColumn(name="seller_id",referencedColumnName = "id")
    private Shop seller;

    @OneToOne
    @JoinColumn(name="buyer_id",referencedColumnName = "id")
    private Customer buyer;

    // 定义一对多关系（单向，全部由Order方进行管理）
    @JoinColumn(name = "order_id") // 指定生成关系表的名称
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}) // 指定级联删除策略
    // PS: 要使用FetchType.EAGER，就不能使用允许重复元素的列表List，否则JPA会报错
    private Set<OrderItem> orderItems = new HashSet<>();

}
