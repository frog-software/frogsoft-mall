package org.frogsoft.mall.controller.v1.api.auth;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.frogsoft.mall.controller.v1.request.auth.AuthRequest;
import org.frogsoft.mall.exception.basic.unauthorized.UnauthorizedException;
import org.frogsoft.mall.security.jwt.JwtTokenProvider;
import org.frogsoft.mall.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

    try {
      String username = authRequest.getUsername();

      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));

      String token = jwtTokenProvider.createToken(authentication);
      Map<Object, Object> model = new HashMap<>();

      model.put("username", username);
      model.put("token", token);

      return new ResponseBodyWrapper<>()
          .status(HttpStatus.CREATED)
          .body(model)
          .build();

    } catch (AuthenticationException e) {
      throw new UnauthorizedException("用户名或密码错误");
    }
  }
}
