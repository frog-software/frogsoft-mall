package org.frogsoft.mall.order.controller.client;

import org.frogsoft.mall.common.model.shop.Shop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 实现跨模块服务调用： Order -> Shop

// 使用url进行服务发现
// TODO：url修改为环境变量
@FeignClient(url = "http://localhost:9210", path = "/", value = "mall-shop")
@Component
public interface ShopClient {
    // 后端内部调用SP01-01的api接口
    @GetMapping("/client/{id}")
    public Shop getShop(@PathVariable(value = "id") Long shop_id);
}
