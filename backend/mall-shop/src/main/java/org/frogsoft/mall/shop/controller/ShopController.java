package org.frogsoft.mall.shop.controller;

import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.commodity.service.CommodityService;
import org.frogsoft.mall.shop.service.ShopService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;
    // private final CommodityService commodityService; TODO：跨模块调用

    /*// SP01-01
    @GetMapping("/{id}")*/

    /*// SP01-02
    @PutMapping("/{id}")*/

    /*// SP02-01
    @GetMapping("/{id}/cargo")*/

    /*// SP02-02
    @PutMapping("/{id}/cargo")*/

    /*// SP02-03
    @DeleteMapping("/{id}/cargo")*/

    /*// SP02-04
    @PostMapping("/{id}/cargo")*/
}
