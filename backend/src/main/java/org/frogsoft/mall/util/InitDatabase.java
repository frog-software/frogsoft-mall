package org.frogsoft.mall.util;

import java.util.Arrays;
import java.util.Optional;
import org.frogsoft.mall.model.user.User;
import org.frogsoft.mall.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitDatabase {

  @Bean
  CommandLineRunner init(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository
  ) {
    return args -> {
      // 存储一个测试用管理员用户
      Optional<User> user = userRepository.findByUsername("admin");
      if (user.isEmpty()) {
        userRepository.save(new User()
            .setPassword(passwordEncoder.encode("admin"))
            .setUsername("admin")
            .setRoles(Arrays.asList("ROLE_ADMIN"))
            .setAvatar("")
            .setRealName(("管理员"))
            .setPhone("18888888888")
        );
      }

    };
  }
}
