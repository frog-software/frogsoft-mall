package org.frogsoft.mall.service.user;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.dto.user.UserDto;
import org.frogsoft.mall.dto.user.UserDtoMapper;
import org.frogsoft.mall.exception.user.UserNotFoundException;
import org.frogsoft.mall.repository.user.UserRepository;
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
