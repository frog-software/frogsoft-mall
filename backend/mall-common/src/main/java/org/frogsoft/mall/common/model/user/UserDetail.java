package org.frogsoft.mall.common.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
// 这个是给 AuthenticationPrincipal 用的
public class UserDetail {

  private Long id;

  private String username;

  private List<String> roles = new ArrayList<>();
}