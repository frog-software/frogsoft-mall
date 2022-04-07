package org.frogsoft.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableCaching
public class MallAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallAuthApplication.class, args);
  }

}
