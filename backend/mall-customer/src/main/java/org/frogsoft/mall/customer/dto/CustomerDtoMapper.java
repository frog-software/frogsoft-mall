package org.frogsoft.mall.customer.dto;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.customer.Customer;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerDtoMapper {

    public CustomerDto toCustomerDto(Customer cm) {

        return new CustomerDto()
                .setId(cm.getId())
                .setUsername(cm.getUsername())
                .setAddressList(cm.getAddressList());

    }
}
