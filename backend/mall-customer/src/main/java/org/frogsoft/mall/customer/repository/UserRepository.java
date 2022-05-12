package org.frogsoft.mall.customer.repository;

import java.util.ArrayList;
import java.util.Optional;
import org.frogsoft.mall.common.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  ArrayList<User> findAllBy();

}
