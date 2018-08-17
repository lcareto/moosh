package com.moosh.example.services.account;

import com.moosh.utils.annotation.EnableClientRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableClientRegistry
@SpringBootApplication
public class AccountServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountServiceApplication.class, args);
  }
}
