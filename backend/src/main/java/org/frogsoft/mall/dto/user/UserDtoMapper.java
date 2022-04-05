package org.frogsoft.mall.dto.user;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.model.user.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDtoMapper {

  public UserDto toUserDto(User user) {

    return new UserDto()
        .setUsername(user.getUsername())
        .setRoles(user.getRoles())
        .setAvatar(user.getAvatar())
        .setPhone(user.getPhone())
        .setRealName(user.getRealName());
  }
}
