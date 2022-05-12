package org.frogsoft.mall.customer.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.customer.Address;

@Getter
@Setter
@Accessors(chain = true)
public class AddressInfoDto {
    private String username;
    private int addressCount;
    private List<Address> addressItems;
}
