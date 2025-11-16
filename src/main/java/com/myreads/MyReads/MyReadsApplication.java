package com.myreads.MyReads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyReadsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyReadsApplication.class, args);
  }
}
