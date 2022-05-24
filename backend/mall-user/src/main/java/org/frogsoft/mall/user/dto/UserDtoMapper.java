package org.frogsoft.mall.user.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.customer.Address;
import org.frogsoft.mall.common.model.customer.BankCard;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDtoMapper {

  public UserDto toUserDtoSimple(
      User user
  ) {

    return new UserDto()
        .setUsername(user.getUsername())
        .setRoles(user.getRoles())
        .setAvatar(user.getAvatar())
        .setPhone(user.getPhone())
        .setNickname(user.getNickname());
  }

  public UserDto toUserDto(
      User user,
      Customer customer,
      Shop shop
  ) {
    String shopName = "";
    Long shopId = 0L;
    if (shop != null) {
      shopName = shop.getShopName();
      shopId = shop.getId();
    }

    ArrayList<Address> addressList = new ArrayList<>();
    ArrayList<BankCard> bankCardList = new ArrayList<>();
    BigDecimal balance = new BigDecimal(0);
    if (customer != null) {
      addressList = customer.getAddressList().stream().collect(Collectors.toCollection(ArrayList::new));
      bankCardList = customer.getBankAccountList().stream().collect(Collectors.toCollection(ArrayList::new));
      balance = customer.getBalance();
    }

    return new UserDto()
        .setUsername(user.getUsername())
        .setRoles(user.getRoles())
        .setAvatar(user.getAvatar())
        .setPhone(user.getPhone())
        .setNickname(user.getNickname())
        .setAddressList(addressList)
        .setAccountList(bankCardList)
        .setBalance(balance)
        .setShopName(shopName)
        .setShopId(shopId);
  }
}
