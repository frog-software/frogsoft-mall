package org.frogsoft.mall.user.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.customer.Address;
import org.frogsoft.mall.common.model.customer.BankCard;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {

  private String username;
  private List<String> roles;
  private String avatar;
  private String phone;
  private String nickname;
  private ArrayList<Address> addressList;
  private ArrayList<BankCard> accountList;
  private BigDecimal balance;
  private String shopName;
  private Long shopId;
}
