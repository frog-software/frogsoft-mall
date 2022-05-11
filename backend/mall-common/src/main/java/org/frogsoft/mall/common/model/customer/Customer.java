package org.frogsoft.mall.common.model.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.user.User;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "customers")
@ToString
public class Customer {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int gender;

    private BigDecimal balance;

    // 定义一对多关系（单向，全部由Customer方进行管理）
    @JoinColumn(name = "customer_id") // 指定生成关系表的名称
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE}) // 指定级联删除策略
    private List<Address> addressList = new ArrayList<>();

    @JoinColumn(name = "customer_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<BankCard> bankAccountList = new ArrayList<>();

    @JoinColumn(name = "customer_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<CartItem> cartItemList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;


}
