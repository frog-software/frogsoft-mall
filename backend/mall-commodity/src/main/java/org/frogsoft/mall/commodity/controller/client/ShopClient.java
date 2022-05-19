package org.frogsoft.mall.commodity.controller.client;

import org.frogsoft.mall.common.model.shop.Shop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// 需要commodity模块调用shop模块，于是使用feign注册一个client类
// 实现跨模块服务调用： Product -> Shop

// 使用url进行服务发现
// TODO：url修改为环境变量
@FeignClient(url = "${shopClient.ribbon.listOfServers}", path = "/", value = "mall-shop")
@Component
public interface ShopClient {
    // 后端内部调用SP01-01的api接口
    @GetMapping("/client/{id}")
    public Shop getShop(@PathVariable(value = "id") Long shop_id);
}
