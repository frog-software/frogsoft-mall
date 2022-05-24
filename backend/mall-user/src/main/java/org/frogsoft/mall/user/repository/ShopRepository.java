package org.frogsoft.mall.user.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShopRepository extends JpaRepository<Shop,Long> {
    Optional<Shop> findByShopName(String name);

    ArrayList<Shop> findAllBy();

    ArrayList<Shop> findAllByOwner_Id(Long owner_id);
}
