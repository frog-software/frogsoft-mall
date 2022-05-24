package org.frogsoft.mall.order.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.cart.CartItem;
import org.frogsoft.mall.common.model.customer.Address;

@Getter
@Setter
@Accessors(chain = true)
public class AddressDto {
    private String receiverPhone;
    private String receiverName;
    // 省、市、县、详细地址
    private String province;
    private String city;
    private String country;
    private String detailAddress;
}
