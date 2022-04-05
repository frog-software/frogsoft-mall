package org.frogsoft.mall.config;

import lombok.AllArgsConstructor;
import org.frogsoft.mall.exception.basic.unauthorized.UnauthorizedException;
import org.frogsoft.mall.exception.user.UserNotFoundException;
import org.frogsoft.mall.repository.user.UserRepository;
import org.frogsoft.mall.security.RestAuthenticationEntryPoint;
import org.frogsoft.mall.security.jwt.JwtTokenAuthenticationFilter;
import org.frogsoft.mall.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
@AllArgsConstructor
public class SecurityConfig {

  @Bean
  SecurityFilterChain springWebFilterChain(
      HttpSecurity http,
      JwtTokenProvider tokenProvider,
      RestAuthenticationEntryPoint restAuthenticationEntryPoint
  ) throws Exception {

    return http.httpBasic(AbstractHttpConfigurer::disable)
        .cors()
        .and()
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(c ->
            c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .authorizeRequests(c -> c
            .antMatchers("/v1/auth/login").permitAll()
            .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(
            new JwtTokenAuthenticationFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE");
      }
    };
  }

  @Bean
  UserDetailsService customUserDetailsService(UserRepository userRepository) {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  @Bean
  AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService,
      PasswordEncoder encoder) {
    return authentication -> {
      String username = authentication.getPrincipal() + "";
      String password = authentication.getCredentials() + "";
      UserDetails user = userDetailsService.loadUserByUsername(username);
      if (!encoder.matches(password, user.getPassword())) {
        throw new UnauthorizedException("密码错误");
      }
      if (!user.isEnabled()) {
        throw new DisabledException("账户未启用");
      }
      return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}