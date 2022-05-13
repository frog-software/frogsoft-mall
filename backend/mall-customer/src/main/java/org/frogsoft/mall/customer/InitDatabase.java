package org.frogsoft.mall.customer;

import java.math.BigDecimal;
import java.util.Optional;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.product.Product;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.customer.repository.CustomerRepository;
import org.frogsoft.mall.customer.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDatabase {

    @Bean
    CommandLineRunner init(
        CustomerRepository customerRepository,
        UserRepository userRepository
    ) {

        return args -> {
            // 存储一个测试顾客
            Optional<Customer> customer = customerRepository.findById(1L);
            Optional<User> user = userRepository.findByUsername("admin");
            if (customer.isEmpty() && !user.isEmpty()) {
                customerRepository.save(new Customer()
                    .setUser(user.get())
                    .setGender(1)
                    .setBalance(BigDecimal.ZERO)
                );
            }



        };
    }
}
