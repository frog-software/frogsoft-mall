package org.frogsoft.mall.customer.controller.api;

import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.frogsoft.mall.customer.controller.request.AddCartItemRequset;
import org.frogsoft.mall.customer.dto.CartDto;
import org.frogsoft.mall.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/") // nacos中设置根url为“customers”
public class CustomerController {
    private final CustomerService customerService;

    // Client api for Other module
    /* 路由中带有“client”的，为后端专属服务调用接口，直接返回model类型 */
    // 获得顾客对象（查找用户名）
    @GetMapping("/client/{username}")
    public Customer getCustomerByUsername(@PathVariable(value = "username") String username){
        return customerService.getCustomerClient(username);
    }
    // 获得顾客对象（查找id）
    @GetMapping("/{id}/client")
    public Customer getCustomerById(@PathVariable(value = "id") Long id){
        return customerService.getCustomerClient(id);
    }


    // api for web app
    // CM03-04：向购物车添加商品
    @PostMapping("/{username}/cart")
    public ResponseEntity<?> addCartItem(
        @PathVariable(value = "username") String username,
        @RequestBody AddCartItemRequset addCartItemRequset,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        CartDto savedCartDto = customerService.saveNewCartItem(addCartItemRequset, username);
        return new ResponseBodyWrapper<CartDto>()
            .status(HttpStatus.CREATED)
            .body(savedCartDto)
            .build();
    }

}
