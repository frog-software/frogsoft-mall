package org.frogsoft.mall.customer;

import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner init(
    ) {

        return args -> {
            // 存储一个测试商店


        };
    }
}
