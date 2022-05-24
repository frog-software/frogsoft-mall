package org.frogsoft.mall.order.controller.client;

import org.frogsoft.mall.common.model.customer.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 实现跨模块服务调用： Order -> Customer

// 使用url进行服务发现
// TODO：url修改为环境变量
@FeignClient(url = "${customerClient.ribbon.listOfServers}", path = "/", value = "mall-customer")
@Component
public interface CustomerClient {
    // 后端内部调用getCustomer的api接口
    @GetMapping("/client/{username}")
    public Customer getCustomerByUsername(@PathVariable(value = "username") String username);

    @GetMapping("/{id}/client")
    public Customer getCustomerById(@PathVariable(value = "id") Long id);

}
