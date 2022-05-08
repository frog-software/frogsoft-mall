package org.frogsoft.mall.commodity.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.commodity.controller.client.ShopClient;
import org.frogsoft.mall.commodity.controller.request.AddProductRequset;
import org.frogsoft.mall.commodity.dto.ProductDto;
import org.frogsoft.mall.commodity.dto.ProductDtoMapper;
import org.frogsoft.mall.commodity.repository.ProductRepository;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Service
public class CommodityService {

  private final ProductRepository productRepository;
  private final ProductDtoMapper productDtoMapper;

  // 通过feign client跨模块调用shop的服务
  private final ShopClient shopClient;

  // 测试用
  public ResponseEntity<?> getAShop(Long shopId){
    return new ResponseBodyWrapper<Shop>()
        .status(HttpStatus.OK)
        .body(shopClient.getShop(shopId))
        .build();
  }

  public ProductDto saveProduct(AddProductRequset addProductRequset, User authenticatedUser){
    // TODO：验证用户的店铺与商品信息上的店铺是否一致
    Shop productInShop = shopClient.getShop(addProductRequset.getShopId());

    Product newProduct = productRepository.save(new Product()
        .setProductName(addProductRequset.getProductName())
        .setCategory(addProductRequset.getCategory())
        .setBrand(addProductRequset.getBrand())
        .setPrice(addProductRequset.getPrice())
        .setDescription(addProductRequset.getDescription())
        .setShop(productInShop)
    );

    return productDtoMapper.toProductDto(newProduct);
  }


  public ArrayList<ProductDto> getAllProducts() {
    return productRepository
        .findAllBy()
        .stream()
        .map(productDtoMapper::toProductDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public ProductDto getSingleProduct(Long id) {
    return productDtoMapper.toProductDto(productRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Product not found.")));
  }


}
