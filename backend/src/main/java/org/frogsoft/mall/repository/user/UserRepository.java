package org.frogsoft.mall.repository.user;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  ArrayList<User> findAllBy();

}
