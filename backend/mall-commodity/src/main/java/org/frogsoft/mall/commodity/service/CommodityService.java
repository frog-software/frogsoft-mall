package org.frogsoft.mall.commodity.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.UserDatabase;
import org.frogsoft.mall.commodity.controller.client.ShopClient;
import org.frogsoft.mall.commodity.controller.request.AddProductRequset;
import org.frogsoft.mall.commodity.dto.ProductDto;
import org.frogsoft.mall.commodity.dto.ProductDtoMapper;
import org.frogsoft.mall.commodity.repository.ProductRepository;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.comment.Comment;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommodityService {

  private final ProductRepository productRepository;
  private final ProductDtoMapper productDtoMapper;

  // 通过feign client跨模块调用shop的服务
  private final ShopClient shopClient;

  // 修改商品信息
  @Transactional
  public ProductDto editProduct(Long id, AddProductRequset addProductRequset, UserDetail authenticatedUser){
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
        .setImageList(addProductRequset.getImageList())
    );
    return productDtoMapper.toProductDto(newProduct);
  }

  // 新建商品
  @Transactional
  public ProductDto saveProduct(AddProductRequset addProductRequset, UserDetail authenticatedUser){
    // TODO：验证用户的店铺与商品信息上的店铺是否一致
    Shop productInShop = shopClient.getShop(addProductRequset.getShopId());

    // 以传入图片列表的第一张图获得头图
    String trumb = "";
    if(addProductRequset.getImageList().size() > 0){
      trumb = addProductRequset.getImageList().get(0);
    }

    Product newProduct = productRepository.save(new Product()
        .setProductName(addProductRequset.getProductName())
        .setCategory(addProductRequset.getCategory())
        .setBrand(addProductRequset.getBrand())
        .setPrice(addProductRequset.getPrice())
        .setDescription(addProductRequset.getDescription())
        .setShop(productInShop)
        .setImageList(addProductRequset.getImageList())
        .setThumb(trumb)
    );

    return productDtoMapper.toProductDto(newProduct);
  }

  // 删除商品
  public void deleteProduct(Long id, UserDetail authenticateUser){
    Product currProduct = productRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Product not Found"));
    currProduct.setShop(null);

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

  // 通过shop查找商品
  public ArrayList<ProductDto> getAllProductsInShop(Long shop_id){
    Shop shop = shopClient.getShop(shop_id);

    return productRepository.findAllByShop(shop)
        .stream()
        .map(productDtoMapper::toProductDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  // 通过关键词查找商品
  public ArrayList<ProductDto> getAllProductsInNameKeyword(String keyword){

    return productRepository.findAllByProductNameContains(keyword)
        .stream()
        .map(productDtoMapper::toProductDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  // 查找一个商品
  public ProductDto getSingleProduct(Long id) {
    return productDtoMapper.toProductDto(productRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Product not found.")));
  }

  // 使用复合条件查找商品（分页返回）
  public ArrayList<ProductDto> getAllProducts(Specification<Product> spec, Pageable pageable) {
    return productRepository
        .findAll(spec,pageable)
        .stream()
        .map(productDtoMapper::toProductDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /*带有“client”的，为提供给后端其他模块的服务调用，直接返回相应model类型*/
  public Product getSingleProductClient(Long product_id) {
    Optional<Product> res = productRepository.findById(product_id);
    return res.orElseThrow(() -> new NotFoundException("Product not found."));
  }


}
