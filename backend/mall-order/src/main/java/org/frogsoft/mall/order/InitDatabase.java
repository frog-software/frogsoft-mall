package org.frogsoft.mall.order;

import java.math.BigDecimal;
import java.util.Optional;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.order.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner init(
        UserRepository userRepository
    ) {

        return args -> {
            // 存储一个测试

        };
    }
}
