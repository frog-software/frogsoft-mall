package org.frogsoft.mall.commodity.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.commodity.controller.request.AddProductRequset;
import org.frogsoft.mall.commodity.dto.ProductDto;
import org.frogsoft.mall.commodity.dto.ProductDtoMapper;
import org.frogsoft.mall.commodity.repository.ProductRepository;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommodityService {

  private final ProductRepository productRepository;
  private final ProductDtoMapper productDtoMapper;

  public ProductDto saveProduct(AddProductRequset addProductRequset, User authenticatedUser){
    // TODO：验证用户的店铺与商品信息上的店铺是否一致
    Product newProduct = productRepository.save(new Product()
        .setProductName(addProductRequset.getProductName())
        .setCategory(addProductRequset.getCategory())
        .setBrand(addProductRequset.getBrand())
        .setPrice(addProductRequset.getPrice())
        .setDescription(addProductRequset.getDescription())
        //.setShop( addProductRequset.getShopId() ) TODO：跨模块调用
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
