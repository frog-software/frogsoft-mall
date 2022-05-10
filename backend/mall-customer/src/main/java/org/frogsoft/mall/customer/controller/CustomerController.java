package org.frogsoft.mall.customer.controller;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.forbidden.ForbiddenException;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.frogsoft.mall.customer.dto.CustomerDto;
import org.frogsoft.mall.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commodities")
public class CustomerController {

    private final CustomerService customerService;

    //CM01-01
    @GetMapping("/{username}/address")
    public ResponseEntity<?> getOneCumstomerAddress(
        @PathVariable(value = "username") String customer_username
    ){
            return new ResponseBodyWrapper<CustomerDto> ()
                    .status(HttpStatus.OK)
                    .body(customerService.getSingleCustomer(customer_username))
                    .build();
    }
}

    //CM01-02
    //@PostMapping("/{username}/address")

    //CM01-03
    //@DeleteMapping("/{username}/address")

    //CM01-04
    //@PutMapping("/{username}/address")
    //@GetMapping("/{username}")




