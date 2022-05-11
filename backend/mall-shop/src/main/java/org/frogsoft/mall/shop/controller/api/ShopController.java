package org.frogsoft.mall.shop.controller.api;

import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.frogsoft.mall.shop.controller.request.AddShopRequset;
import org.frogsoft.mall.shop.dto.ShopDto;
import org.frogsoft.mall.shop.service.ShopService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ShopController {
    private final ShopService shopService;

    // Client api for Mall-Commodity
    /* 路由中带有“client”的，为后端专属服务调用接口，直接返回model类型 */
    @GetMapping("/client/{id}")
    public Shop getShop(@PathVariable(value = "id") Long shop_id){
        return shopService.getSingleShopClient(shop_id);
    }

    // api for web app
    // SP01-01：获得商店基本信息
    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleShopInfo(
        @PathVariable(value = "id") Long shop_id,
        @AuthenticationPrincipal User authenticatedUser
    ){
        return new ResponseBodyWrapper<ShopDto>()
            .status(HttpStatus.OK)
            .body(shopService.getSingleShop(shop_id))
            .build();
    }


    // SP01-02：修改商店基本信息
    @PutMapping("/{id}")
    public ResponseEntity<?> putSingleShop(
        @PathVariable(value = "id") Long shop_id,
        @RequestBody AddShopRequset addShopRequset,
        @AuthenticationPrincipal User authenticatedUser
    )
    {
        return new ResponseBodyWrapper<ShopDto>()
            .status(HttpStatus.CREATED)
            .body(shopService.editShop(shop_id, addShopRequset, authenticatedUser))
            .build();
    }

     // SP01-03：新建商店
    @PostMapping("/")
    public ResponseEntity<?> addShop(
        @RequestBody AddShopRequset addShopRequset,
        @AuthenticationPrincipal User authenticatedUser
    ){
        ShopDto savedShopDto = shopService.saveShop(addShopRequset, authenticatedUser);
        return new ResponseBodyWrapper<ShopDto>()
            .status(HttpStatus.CREATED)
            .body(savedShopDto)
            .build();
    }

    // SP01-04：删除商店
    @PutMapping("/{id}")
    public ResponseEntity<?> deleteShop(
        @PathVariable(value = "id") Long shop_id,
        @AuthenticationPrincipal User authenticatedUser
    )
    {
        shopService.deleteShop(shop_id, authenticatedUser);
        return ResponseEntity.noContent().build();
    }

    /*// SP02-01
    @GetMapping("/{id}/cargo")*/

    /*// SP02-02
    @PutMapping("/{id}/cargo")*/

    /*// SP02-03
    @DeleteMapping("/{id}/cargo")*/

    /*// SP02-04
    @PostMapping("/{id}/cargo")*/
}
