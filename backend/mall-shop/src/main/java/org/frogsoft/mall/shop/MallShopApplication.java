package org.frogsoft.mall.shop;

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
    ManagementWebSecurityAutoConfiguration.class})
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableScheduling
@EnableCaching
@EnableJpaRepositories(basePackages = {"org.frogsoft.mall.common.model",
    "org.frogsoft.mall.shop.repository"})
@ComponentScan(basePackages = {"org.frogsoft.mall.common.model", "org.frogsoft.mall.shop"})
@EntityScan(basePackages = {"org.frogsoft.mall.common.model", "org.frogsoft.mall.shop.repository"})
public class MallShopApplication {
  public static void main(String[] args) {
    org.springframework.boot.SpringApplication.run(MallShopApplication.class, args);
  }
}
