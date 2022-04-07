package org.frogsoft.mall.auth.exception.user;

import org.frogsoft.mall.auth.exception.basic.conflict.ConflictException;

public class UserConflictException extends ConflictException {

  public UserConflictException(String username) {
    super("用户名 " + username + " 已被使用");
  }
}
