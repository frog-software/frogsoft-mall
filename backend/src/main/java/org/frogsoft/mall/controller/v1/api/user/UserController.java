package org.frogsoft.mall.controller.v1.api.user;

import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.dto.user.UserDto;
import org.frogsoft.mall.exception.basic.forbidden.ForbiddenException;
import org.frogsoft.mall.model.user.User;
import org.frogsoft.mall.service.user.UserService;
import org.frogsoft.mall.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @RolesAllowed("ROLE_ADMIN")
  @GetMapping("")
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
