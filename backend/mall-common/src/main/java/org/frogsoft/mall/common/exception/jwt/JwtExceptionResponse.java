package org.frogsoft.mall.common.exception.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.frogsoft.mall.common.util.ResponseBodyWrapper;
import org.springframework.http.HttpStatus;

public class JwtExceptionResponse {

  private static void buildResponse(
      String message,
      ServletResponse servletResponse
  ) throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    ResponseBodyWrapper<String> responseBody = new ResponseBodyWrapper<String>()
        .status(HttpStatus.UNAUTHORIZED)
        .message(message)
        .body(message);

    servletResponse.setCharacterEncoding("utf-8");

    servletResponse.getWriter().write(objectMapper.writeValueAsString(responseBody));
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

  }

  public static void buildInvalidTokenResponse(ServletResponse servletResponse) throws IOException {
    buildResponse("Token 无效", servletResponse);
  }
}
