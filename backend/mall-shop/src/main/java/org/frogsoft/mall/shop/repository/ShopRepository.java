package org.frogsoft.mall.shop.repository;

import java.util.ArrayList;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface ShopRepository extends JpaRepository<Shop,Long> {
    Optional<Shop> findByShopName(String name);

    ArrayList<Shop> findAllBy();

    ArrayList<Shop> findAllByOwner_Id(Long owner_id);
}
