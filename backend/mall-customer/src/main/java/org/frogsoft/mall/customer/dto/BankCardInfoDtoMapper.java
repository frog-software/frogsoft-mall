package org.frogsoft.mall.customer.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BankCardInfoDtoMapper {
    public BankCardInfoDto toBankCardInfoDto(Customer customer){
        User user = customer.getUser();
        if(user==null){
            throw new NotFoundException("User not found.");
        }
        return new BankCardInfoDto()
            .setUsername(user.getUsername())
            .setBankAccountItems(customer.getBankAccountList())
            .setBankAccountCount(customer.getBankAccountList().size());
    }
}
