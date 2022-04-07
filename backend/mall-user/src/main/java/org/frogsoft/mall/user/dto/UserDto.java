package org.frogsoft.mall.user.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {

  private String username;
  private List<String> roles;
  private String avatar;
  private String phone;
  private String realName;
}
