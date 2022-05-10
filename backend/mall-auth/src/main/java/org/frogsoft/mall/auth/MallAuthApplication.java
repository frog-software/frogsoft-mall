package org.frogsoft.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableScheduling
@EnableCaching
@EnableJpaRepositories(basePackages = {"org.frogsoft.mall.common.model",
    "org.frogsoft.mall.auth.repository"})
@ComponentScan(basePackages = {"org.frogsoft.mall.common", "org.frogsoft.mall.auth"})
@EntityScan(basePackages = {"org.frogsoft.mall.common.model"})
public class MallAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallAuthApplication.class, args);
  }

}
