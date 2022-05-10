package org.frogsoft.mall.customer.service;

import java.util.ArrayList;
import java.util.stream.Collectors;


import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.customer.dto.CustomerDto;
import org.frogsoft.mall.customer.dto.CustomerDtoMapper;
import org.frogsoft.mall.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    public ArrayList<CustomerDto> getAllCustomers() {
        return customerRepository
                .findAllBy()
                .stream()
                .map(customerDtoMapper::toCustomerDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public CustomerDto getSingleCustomer(String username) {
        return customerDtoMapper.toCustomerDto(customerRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Customer not found.")));
    }
}
