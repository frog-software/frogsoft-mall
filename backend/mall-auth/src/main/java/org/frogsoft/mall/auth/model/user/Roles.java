package org.frogsoft.mall.auth.model.user;

public enum Roles {
  ROLE_ADMIN("ROLE_ADMIN");

  final String role;

  Roles(String role) {
    this.role = role;
  }

  public String getRole() {
    return this.role;
  }
}