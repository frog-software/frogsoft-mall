package org.frogsoft.mall.controller.v1.request.auth;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class AuthRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 32974805623333756L;
  private String username;
  private String password;
}