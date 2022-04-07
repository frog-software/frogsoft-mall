package org.frogsoft.mall.user.controller;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.forbidden.ForbiddenException;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.frogsoft.mall.user.dto.UserDto;
import org.frogsoft.mall.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

  private final UserService userService;

  @GetMapping("/")
  public ResponseEntity<?> getAllUsers() {
    return new ResponseBodyWrapper<ArrayList<UserDto>>()
        .status(HttpStatus.OK)
        .body(userService.getAllUsers())
        .build();
  }


  @GetMapping("/{username}")
  public ResponseEntity<?> getOneUser(
      @PathVariable(value = "username") String username,
      @AuthenticationPrincipal User authenticatedUser
  ) {

    if (!(username.equals(authenticatedUser.getUsername())
        || authenticatedUser.getRoles().contains("ROLE_ADMIN")
    )) {
      throw new ForbiddenException("你只能查看自己的信息");
    }

    return new ResponseBodyWrapper<UserDto>()
        .status(HttpStatus.OK)
        .body(userService.getSingleUser(username))
        .build();
  }

}
