package org.frogsoft.mall.user.controller.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegisterRequest {
  @NotNull
  private String username;
  @NotNull
  private String nickname;
  @NotNull
  private String password;
  @NotNull
  private String phone;
  private String avatar;
  private String code;
}
