package org.frogsoft.mall.order.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.customer.Address;
import org.frogsoft.mall.common.model.order.Order;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressDtoMapper {
    public AddressDto toAddressDto(Address address){
        return new AddressDto()
            .setReceiverName(address.getReceiverName())
            .setReceiverPhone(address.getReceiverPhone())
            .setCity(address.getCity())
            .setProvince(address.getProvince())
            .setCountry(address.getCountry())
            .setDetailAddress(address.getDetailAddress());
    }
}
