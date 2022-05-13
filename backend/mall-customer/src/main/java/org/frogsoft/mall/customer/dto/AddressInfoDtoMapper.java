package org.frogsoft.mall.customer.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor

public class AddressInfoDtoMapper {
    public AddressInfoDto toAddressInfoDto(Customer customer){
        User user = customer.getUser();
        if(user==null){
            throw new NotFoundException("User not found.");
        }
        return new AddressInfoDto()
            .setUsername(user.getUsername())
            .setAddressItems(customer.getAddressList())
            .setAddressCount(customer.getAddressList().size());
    }
}
