package org.frogsoft.mall.shop;

import java.util.Optional;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.shop.repository.ShopRepository;
import org.frogsoft.mall.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner init(
        ShopRepository shopRepository,
        UserRepository userRepository
    ) {

        return args -> {
            // 存储一个测试商店
            Optional<Shop> shop = shopRepository.findByShopName("test_shop");
            if (shop.isEmpty()) {
                shopRepository.save(new Shop()
                    .setShopName("test_shop")
                    .setRate(5.0f)
                    .setTradeQuantity(0)
                    .setOwner(userRepository.getById(1L))
                );
            }

        };
    }
}
