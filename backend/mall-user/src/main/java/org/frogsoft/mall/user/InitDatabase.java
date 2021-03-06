package org.frogsoft.mall.user;

import java.util.Arrays;
import java.util.Optional;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitDatabase {

  @Bean
  CommandLineRunner init(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository) {
    return args -> {
      // 存储一个测试用管理员用户
      Optional<User> user = userRepository.findByUsername("admin");
      if (user.isEmpty()) {
        userRepository.save(new User()
            .setPassword(passwordEncoder.encode("admin"))
            .setUsername("admin")
            .setRoles(Arrays.asList("ROLE_ADMIN"))
            .setAvatar("")
            .setNickname("管理员")
            .setPhone("18888888888"));
      }
      // 存储一个测试用管理员用户
      user = userRepository.findByUsername("user");
      if (user.isEmpty()) {
        userRepository.save(new User()
            .setPassword(passwordEncoder.encode("user"))
            .setUsername("user")
            .setRoles(Arrays.asList("ROLE_USER"))
            .setAvatar("")
            .setNickname("用户")
            .setPhone("13333333333"));
      }
    };
  }
}
