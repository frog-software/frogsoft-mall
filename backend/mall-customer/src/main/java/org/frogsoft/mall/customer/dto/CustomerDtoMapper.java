package org.frogsoft.mall.customer.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.user.User;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerDtoMapper {
    public CustomerDto toCustomerDto(Customer customer){

        User user = customer.getUser();
        if(user==null){
            throw new NotFoundException("User not found.");
        }

        return new CustomerDto()
            .setGender(customer.getGender())
            .setBalance(customer.getBalance())
            .setUser(customer.getUser())
            .setSavedAddressCount(customer.getAddressList().size())
            .setSavedBankAccountCount(customer.getBankAccountList().size())
            .setShopCartItemCount(customer.getCartItemList().size());

    }
}
