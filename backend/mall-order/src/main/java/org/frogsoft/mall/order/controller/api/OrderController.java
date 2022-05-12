package org.frogsoft.mall.order.controller.api;

import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
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
@RequestMapping("/") // nacos中设置根url为“users”
public class OrderController {


    // Client api for Other module
    /* 路由中带有“client”的，为后端专属服务调用接口，直接返回model类型 */



    // api for web app


}
