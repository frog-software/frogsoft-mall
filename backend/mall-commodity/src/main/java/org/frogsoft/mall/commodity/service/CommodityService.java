package org.frogsoft.mall.commodity.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.commodity.dto.ProductDto;
import org.frogsoft.mall.commodity.dto.ProductDtoMapper;
import org.frogsoft.mall.commodity.repository.ProductRepository;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommodityService {

  private final ProductRepository productRepository;
  private final ProductDtoMapper productDtoMapper;

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
