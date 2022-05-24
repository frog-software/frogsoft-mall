package org.frogsoft.mall.customer.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.user.User;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerDto {

    private Long customerId;
    private User user;
    private int gender;
    private BigDecimal balance;

    // 计数
    private int savedAddressCount;
    private int savedBankAccountCount;
    private int shopCartItemCount;

}

