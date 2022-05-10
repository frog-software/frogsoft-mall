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
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommodityService {

  private final ProductRepository productRepository;
  private final ProductDtoMapper productDtoMapper;

  // 通过feign client跨模块调用shop的服务
  private final ShopClient shopClient;

 /* // 测试用
  public ResponseEntity<?> getAShop(Long shopId){
    return new ResponseBodyWrapper<Shop>()
        .status(HttpStatus.OK)
        .body(shopClient.getShop(shopId))
        .build();
  }*/

  // 修改商品信息
  public ProductDto editProduct(Long id, AddProductRequset addProductRequset, User authenticatedUser){
    Product currProduct = productRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Product not Found"));
    Shop productInShop = shopClient.getShop(addProductRequset.getShopId());
    Product newProduct = productRepository.save(currProduct
        .setProductName(addProductRequset.getProductName())
        .setCategory(addProductRequset.getCategory())
        .setBrand(addProductRequset.getBrand())
        .setPrice(addProductRequset.getPrice())
        .setDescription(addProductRequset.getDescription())
        .setShop(productInShop)
    );
    return productDtoMapper.toProductDto(newProduct);
  }

  // 新建商品
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

  // 删除商品
  public void deleteProduct(Long id, User authenticateUser){
    Product currProduct = productRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Product not Found"));
    currProduct.setShop(null);
    // TODO：级联删除评论（如果不能自动的话）
    currProduct.setCommentList(null);
    Product savedProduct = productRepository.save(currProduct);
    productRepository.delete(savedProduct);
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
