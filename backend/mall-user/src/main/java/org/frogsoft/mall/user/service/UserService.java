package org.frogsoft.mall.user.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.user.UserNotFoundException;
import org.frogsoft.mall.user.dto.UserDto;
import org.frogsoft.mall.user.dto.UserDtoMapper;
import org.frogsoft.mall.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;

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
}
