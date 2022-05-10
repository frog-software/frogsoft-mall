package org.frogsoft.mall.customer.dto;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerDto {
    private String username;
    private Long id;//账号
    private Object[] addressList;

}

