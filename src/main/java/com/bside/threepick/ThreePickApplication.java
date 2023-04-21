package com.bside.threepick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaRepositories
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
public class ThreePickApplication {

  public static void main(String[] args) {
    SpringApplication.run(ThreePickApplication.class, args);
  }

}
