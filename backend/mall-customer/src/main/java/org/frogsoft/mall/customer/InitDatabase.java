package org.frogsoft.mall.customer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitDatabase {
    Object[] addresslist=new Object[]{"haidian","beijingxizhan"};
    String[] accountlist= new String[]{"111","222"};
    Object[] purchasehistory=new Object[]{"2022.4.17 orange 40g 15$"};
    @Bean
    CommandLineRunner init(
            CustomerRepository customerRepository
    ) {
        return args -> {
            // 存储一个测试顾客hanmeng
            Optional<Customer> customer = customerRepository.findByUsername("hanmeng");
            if (customer.isEmpty()) {
                customerRepository.save(new Customer()
                        .setId(40129L)
                        .setGender(1)//女
                        .setAddressList(addresslist)
                        .setAccountList(accountlist)
                        .setPurchaseHistory(purchasehistory)
                        .setBalance(1000)
                        .setAvatar("http://///")
                        .setPhoneNum("158102331111")
                        .setRealName("hanmeng")
                        .setPassword("040129")

                );
            }

        };
    }
}
