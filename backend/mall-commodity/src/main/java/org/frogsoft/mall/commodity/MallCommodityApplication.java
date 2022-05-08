package org.frogsoft.mall.commodity;

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
    "org.frogsoft.mall.commodity.repository"})
@ComponentScan(basePackages = {"org.frogsoft.mall.common.model", "org.frogsoft.mall.commodity"})
@EntityScan(basePackages = {"org.frogsoft.mall.common.model", "org.frogsoft.mall.commodity.repository"})
@EnableFeignClients // 增加Feign服务
public class MallCommodityApplication {

  public static void main(String[] args) {
    org.springframework.boot.SpringApplication.run(MallCommodityApplication.class, args);
  }
}
