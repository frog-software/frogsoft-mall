package org.frogsoft.mall.order.controller.client;

import org.frogsoft.mall.common.model.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 实现跨模块服务调用：Order -> Product

// 使用url进行服务发现
// TODO：url修改为环境变量
@FeignClient(url = "http://localhost:9888", path = "/", value = "mall-product")
@Component
public interface ProductClient {
    // 后端内部调用getProduct的api接口
    @GetMapping("/client/{id}")
    public Product getProduct(@PathVariable(value = "id") Long product_id);
}
