package org.frogsoft.mall.order.controller.api;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.frogsoft.mall.order.controller.request.AddOrderRequest;
import org.frogsoft.mall.order.controller.request.EditOrderRequestSeller;
import org.frogsoft.mall.order.controller.request.OrderLogInfoRequest;
import org.frogsoft.mall.order.controller.request.OrderPayingRequest;
import org.frogsoft.mall.order.dto.OrderDto;
import org.frogsoft.mall.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/") // nacos中设置根url为“orders”
public class OrderController {

    public final OrderService orderService;

    // Client api for Other module
    /* 路由中带有“client”的，为后端专属服务调用接口，直接返回model类型 */


    // api for web app
    // OD01-01
    @PostMapping("/customer")
    public ResponseEntity<?> addOrderCustomer(
        @RequestBody AddOrderRequest addOrderRequest,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.CREATED)
            .body(orderService.saveOrder(addOrderRequest))
            .build();
    }

    // OD01-02
    @GetMapping("/{id}/customer")
    public ResponseEntity<?> getSingleOrderCustomer(
        @PathVariable(value = "id") Long order_id,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.OK)
            .body(orderService.getSingleOrder(order_id))
            .build();
    }


    // OD01-03
    @PutMapping("/{id}/customer")
    public ResponseEntity<?> editSingleOrderCustomer(
        @PathVariable(value = "id") Long order_id,
        @RequestBody AddOrderRequest addOrderRequest,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.CREATED)
            .body(orderService.editOrderCustomer(order_id, addOrderRequest))
            .build();
    }

    // OD01-04
    @DeleteMapping("/{id}/customer")
    public ResponseEntity<?> deleteSingleOrderCustomer(
        @PathVariable(value = "id") Long order_id,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        orderService.deleteOrderCustomer(order_id, authenticatedUser);
        return ResponseEntity.noContent().build();
    }

    // OD01-05
    @GetMapping("/customer/all")
    public ResponseEntity<?> getAllOrderCustomer(
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<ArrayList<OrderDto>>()
            .status(HttpStatus.OK)
            .body(orderService.getAllOrderCustomer(authenticatedUser))
            .build();
    }

    // OD01-06
    @PostMapping("/{id}/customer/payment")
    public ResponseEntity<?> payOrderCustomer(
        @PathVariable(value = "id") Long order_id,
        @RequestBody OrderPayingRequest orderPayingRequest,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.CREATED)
            .body(orderService.editOrderPaymentCustomer(order_id,orderPayingRequest))
            .build();
    }

    // OD01-07
    @PutMapping("/{id}/customer/receiving")
    public ResponseEntity<?> receivingOrderCustomer(
        @PathVariable(value = "id") Long order_id,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.CREATED)
            .body(orderService.editOrderReceivingCustomer(order_id))
            .build();
    }


    // OD02-01
    @PutMapping("/{id}/seller")
    public ResponseEntity<?> editSingleOrderSeller(
        @PathVariable(value = "id") Long order_id,
        @RequestBody EditOrderRequestSeller editOrderRequestSeller,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.CREATED)
            .body(orderService.editOrderSeller(order_id, editOrderRequestSeller, authenticatedUser))
            .build();
    }

    // OD02-02
    @PostMapping("/{id}/seller")
    public ResponseEntity<?> uploadOrderLogInfoSeller(
        @PathVariable(value = "id") Long order_id,
        @RequestBody OrderLogInfoRequest orderLogInfoRequest,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.CREATED)
            .body(orderService.editOrderLogisticsSeller(order_id,orderLogInfoRequest))
            .build();
    }

    // OD02-03
    @DeleteMapping("/{id}/seller")
    public ResponseEntity<?> closeSingleOrderSeller(
        @PathVariable(value = "id") Long order_id,
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<OrderDto>()
            .status(HttpStatus.CREATED)
            .body(orderService.closeOrderSeller(order_id, authenticatedUser))
            .build();
    }

    // OD02-04
    @GetMapping("/seller")
    public ResponseEntity<?> getAllOrderSeller(
        @AuthenticationPrincipal UserDetail authenticatedUser
    ){
        return new ResponseBodyWrapper<ArrayList<OrderDto>>()
            .status(HttpStatus.OK)
            .body(orderService.getAllOrderSeller(authenticatedUser))
            .build();
    }
}
