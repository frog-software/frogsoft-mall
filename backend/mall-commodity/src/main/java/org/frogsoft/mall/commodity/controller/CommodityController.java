package org.frogsoft.mall.commodity.controller;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.commodity.dto.ProductDto;
import org.frogsoft.mall.common.exception.basic.forbidden.ForbiddenException;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.commodity.service.CommodityService;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commodities")
public class CommodityController {

    private final CommodityService commodityService;

/*    // PD01-01
    @PostMapping("/{id}/simple")*/

    // PD01-02
    @GetMapping("/{id}/simple")
    public ResponseEntity<?> getOneProduct(
        @PathVariable(value = "id") Long product_id
    ) {

        return new ResponseBodyWrapper<ProductDto>()
            .status(HttpStatus.OK)
            .body(commodityService.getSingleProduct(product_id))
            .build();
    }

/*    // PD01-03
    @PutMapping("/{id}/simple")

    // PD01-04
    @DeleteMapping("/{id}/simple")*/

    // PD01-05
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseBodyWrapper<ArrayList<ProductDto>>()
            .status(HttpStatus.OK)
            .body(commodityService.getAllProducts())
            .build();
    }


}