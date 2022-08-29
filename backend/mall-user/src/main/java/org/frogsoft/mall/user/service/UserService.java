package org.frogsoft.mall.user.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.badrequest.BadRequestException;
import org.frogsoft.mall.common.exception.basic.notfound.NotFoundException;
import org.frogsoft.mall.common.exception.user.UserNotFoundException;
import org.frogsoft.mall.common.exception.user.UserConflictException;
import org.frogsoft.mall.common.model.customer.Customer;
import org.frogsoft.mall.common.model.shop.Shop;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.user.controller.request.RegisterRequest;
import org.frogsoft.mall.user.dto.UserDto;
import org.frogsoft.mall.user.dto.UserDtoMapper;
import org.frogsoft.mall.user.repository.AddressRepository;
import org.frogsoft.mall.user.repository.BankCardRepository;
import org.frogsoft.mall.user.repository.CustomerRepository;
import org.frogsoft.mall.user.repository.ShopRepository;
import org.frogsoft.mall.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;
  private final AddressRepository addressRepository;
  private final BankCardRepository bankCardRepository;
  private final UserDtoMapper userDtoMapper;
  private final PasswordEncoder passwordEncoder;
  private final ShopRepository shopRepository;

  public ArrayList<UserDto> getAllUsers() {
    return userRepository
        .findAllBy()
        .stream()
        .map(userDtoMapper::toUserDtoSimple)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public UserDto getSingleUser(String username) {
    var user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    var customerOp = customerRepository.findByUser(user);
    Customer customer = null;
    if (customerOp.isPresent()) {
      customer = customerOp.get();
    }
    var shops = shopRepository.findAllByOwner_Id(user.getId());
    Shop shop = null;
    if (shops.size() >= 1) {
      shop = shops.get(0);
    }

    return userDtoMapper.toUserDto(user, customer, shop);
  }

  // 使用事务
  @Transactional
  public UserDto registerUser(RegisterRequest registerRequest) {
    // 检查空输入
    if (registerRequest.getUsername().isEmpty()
      || registerRequest.getNickname().isEmpty()
      || registerRequest.getPhone().isEmpty()
      || registerRequest.getPassword().isEmpty()
    ) {
      throw new BadRequestException("用户名、昵称、手机号和密码不能为空");
    }

    if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
      throw new UserConflictException("用户名已存在");
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
    userRepository.save(newUser); // 必须先写入用户，再写入顾客数据库
    customerRepository.save(newCustomer);

    return userDtoMapper.toUserDto(newUser, newCustomer, null);
  }
}
