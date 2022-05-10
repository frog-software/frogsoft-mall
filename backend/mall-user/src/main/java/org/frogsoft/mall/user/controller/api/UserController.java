package org.frogsoft.mall.user.controller.api;

import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.common.exception.basic.forbidden.ForbiddenException;
import org.frogsoft.mall.common.exception.basic.unauthorized.UnauthorizedException;
import org.frogsoft.mall.common.model.user.User;
import org.frogsoft.mall.common.model.user.UserDetail;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.frogsoft.mall.user.controller.request.RegisterRequest;
import org.frogsoft.mall.user.dto.UserDto;
import org.frogsoft.mall.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
// 这里相当于 /users ，因为是 Gateway 转发过来的请求
@RequestMapping("/")
public class UserController {

  private final UserService userService;

  @GetMapping("/")
  @RolesAllowed({"ROLE_ADMIN"})
  public ResponseEntity<?> getAllUsers() {
    return new ResponseBodyWrapper<ArrayList<UserDto>>()
        .status(HttpStatus.OK)
        .body(userService.getAllUsers())
        .build();
  }


  @GetMapping("/{username}")
  public ResponseEntity<?> getOneUser(
      @PathVariable(value = "username") String username,
      @AuthenticationPrincipal UserDetail authenticatedUser
  ) {

    // 这个只是测试的时候会是 null, 其实是不需要的 (因为可以配置这个接口强制鉴权，就不会是 null 了)
    if (authenticatedUser == null) {
      throw new UnauthorizedException("你没有登录");
    }

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

  @PostMapping("/")
  public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
    return new ResponseBodyWrapper<UserDto>()
        .status(HttpStatus.CREATED)
        .body(userService.registerUser(registerRequest))
        .build();
  }

}
