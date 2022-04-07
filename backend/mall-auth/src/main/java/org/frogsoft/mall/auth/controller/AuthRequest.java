package org.frogsoft.mall.auth.controller;

import java.io.Serializable;
import lombok.Data;

@Data
public class AuthRequest implements Serializable {

  private String username;
  private String password;
}