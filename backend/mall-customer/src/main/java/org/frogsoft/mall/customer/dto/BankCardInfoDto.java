package org.frogsoft.mall.customer.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.frogsoft.mall.common.model.customer.BankCard;

@Getter
@Setter
@Accessors(chain = true)
public class BankCardInfoDto {
    private String username;
    private int bankAccountCount;
    private List<BankCard> bankAccountItems;
}
