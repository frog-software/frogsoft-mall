package org.frogsoft.mall.commodity.controller.api;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.commodity.controller.request.AddProductRequset;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.request.ClientGetProductsRequest;
import org.frogsoft.mall.commodity.dto.ProductDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class CommodityController {

    private final CommodityService commodityService;

    /* 路由中带有“client”的，为后端专属服务调用接口，直接返回model类型 */
    // Client api for Mall-Customer
    @GetMapping("/client/{id}")
    public Product getProduct(@PathVariable(value = "id") Long product_id){
        return commodityService.getSingleProductClient(product_id);
    }

    // api for web app
    // PD01-01 为商店添加商品
    @PostMapping("/simple")
    public ResponseEntity<?> addProductToShop(
        @RequestBody AddProductRequset addProductRequset
    ){
        ProductDto savedProduct = commodityService.saveProduct(addProductRequset,null);
        return new ResponseBodyWrapper<ProductDto>()
            .status(HttpStatus.CREATED)
            .body(savedProduct)
            .build();
    }

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

    // PD01-03
    @PutMapping("/{id}/simple")
    public ResponseEntity<?> putOneProduct(
        @PathVariable(value = "id") Long product_id,
        @RequestBody AddProductRequset putProductRequset,
        @AuthenticationPrincipal User authenticatedUser
    )
    {
        return new ResponseBodyWrapper<ProductDto>()
            .status(HttpStatus.CREATED)
            .body(commodityService.editProduct(product_id, putProductRequset, authenticatedUser))
            .build();
    }

    // PD01-04
    @DeleteMapping("/{id}/simple")
    public ResponseEntity<?> deleteOneProduct(
        @PathVariable(value = "id") Long product_id,
        @AuthenticationPrincipal User authenticatedUser
    )
    {
        commodityService.deleteProduct(product_id, authenticatedUser);
        return ResponseEntity.noContent().build();
    }

    // PD01-05：查找所有商品
    // TODO：分页、复合查询和排序？
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(
        @RequestParam(value = "shop_id",required = false) Long shop_id,
        @RequestParam(value = "keyword",required = false) String keyword,
        @RequestParam(value = "orderby",required = false) String orderby
    ) {
        ArrayList<ProductDto> res_body = null;

        if(shop_id != null){
            res_body = commodityService.getAllProductsInShop(shop_id);
        }

        if(keyword != null){
            res_body = commodityService.getAllProductsInNameKeyword(keyword);
        }

        if(res_body == null){
            return ResponseEntity.noContent().build();
        }

        return new ResponseBodyWrapper<ArrayList<ProductDto>>()
            .status(HttpStatus.OK)
            .body(res_body)
            .build();
    }



}
