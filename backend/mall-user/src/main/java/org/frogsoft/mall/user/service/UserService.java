package org.frogsoft.mall.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.badrequest.BadRequestException;
import org.frogsoft.mall.common.exception.user.UserNotFoundException;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.user.controller.request.RegisterRequest;
import org.frogsoft.mall.user.dto.UserDto;
import org.frogsoft.mall.user.dto.UserDtoMapper;
import org.frogsoft.mall.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;
  private final PasswordEncoder passwordEncoder;

  public ArrayList<UserDto> getAllUsers() {
    return userRepository
        .findAllBy()
        .stream()
        .map(userDtoMapper::toUserDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public UserDto getSingleUser(String username) {
    return userDtoMapper.toUserDto(userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username)));
  }

  public UserDto registerUser(RegisterRequest registerRequest) {
    // 检查空输入
    if (registerRequest.getUsername().isEmpty()
      || registerRequest.getNickname().isEmpty()
      || registerRequest.getPhone().isEmpty()
      || registerRequest.getPassword().isEmpty()
    ) {
      throw new BadRequestException("用户名、昵称、手机号和密码不能为空");
    }

    User newUser = new User();
    newUser
        .setUsername(registerRequest.getUsername())
        .setNickname(registerRequest.getNickname())
        .setPhone(registerRequest.getPhone())
        .setPassword(passwordEncoder.encode(registerRequest.getPassword()))
        .setAvatar(registerRequest.getAvatar());

    // 默认注册用户为买家，为其创建数据对象
    Customer newCustomer = new Customer()
        .setUser(newUser)
        .setBalance(BigDecimal.ZERO)
        .setGender(0);

    return userDtoMapper.toUserDto(userRepository.save(newUser));
  }
}
