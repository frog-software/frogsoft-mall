package org.frogsoft.mall.shop.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.shop.controller.request.AddShopRequset;
import org.frogsoft.mall.shop.dto.ShopDto;
import org.frogsoft.mall.shop.dto.ShopDtoMapper;
import org.frogsoft.mall.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopService {

  private final ShopRepository shopRepository;
  private final ShopDtoMapper shopDtoMapper;

  // 获得所有的商店
  public ArrayList<ShopDto> getAllShops() {
    return shopRepository
        .findAllBy()
        .stream()
        .map(shopDtoMapper::toShopDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  // 通过id查找商店
  public ShopDto getSingleShop(Long id) {
    return shopDtoMapper.toShopDto(shopRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Shop not found.")));
  }

  // 新建一个商店
  public ShopDto saveShop(AddShopRequset addShopRequset, User authenticatedUser){

    Shop newShop = shopRepository.save(new Shop()
        .setShopName(addShopRequset.getShopName())
        .setShopImage(addShopRequset.getShopImage())
        .setOwner(authenticatedUser)
        .setRate(0f)
        .setTradeQuantity(0));

    return shopDtoMapper.toShopDto(newShop);
  }

  // 修改商店信息
  public ShopDto editShop(Long id, AddShopRequset addShopRequset, User authenticatedUser){
    Shop currShop = shopRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Shop not Found"));

    // TODO：仅店主本人可以修改信息
    Shop newShop = shopRepository.save(currShop
        .setShopName(addShopRequset.getShopName())
        .setShopImage(addShopRequset.getShopImage()));

    return shopDtoMapper.toShopDto(newShop);
  }

  // 删除商店
  public void deleteShop(Long id, User authenticatedUser){
    Shop currShop = shopRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Shop not Found"));
    currShop.setOwner(null);

    // TODO：级联删除商店内的商品（如果需要）
    Shop savedShop = shopRepository.save(currShop);
    shopRepository.delete(savedShop);
  }


  /*带有“client”的，为后端专属服务调用，直接返回相应model类型*/
  public Shop getSingleShopClient(Long id){
    Optional<Shop> res = shopRepository.findById(id);
    return res.orElseThrow(() -> new NotFoundException("Shop not found."));
  }
}
