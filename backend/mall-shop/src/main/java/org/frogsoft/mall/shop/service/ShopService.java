package org.frogsoft.mall.shop.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.shop.dto.ShopDto;
import org.frogsoft.mall.shop.dto.ShopDtoMapper;
import org.frogsoft.mall.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopService {

  private final ShopRepository shopRepository;
  private final ShopDtoMapper shopDtoMapper;

  public ArrayList<ShopDto> getAllShops() {
    return shopRepository
        .findAllBy()
        .stream()
        .map(shopDtoMapper::toShopDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public ShopDto getSingleProduct(Long id) {
    return shopDtoMapper.toShopDto(shopRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Shop not found.")));
  }
}
