package com.vscoding.apps.yugioh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class YugiohApplication {

  public static void main(String[] args) {
    SpringApplication.run(YugiohApplication.class, args);
  }
}
