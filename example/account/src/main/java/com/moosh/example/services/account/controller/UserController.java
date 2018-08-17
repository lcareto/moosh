package com.moosh.example.services.account.controller;

import com.moosh.example.services.account.dto.User;
import com.moosh.example.services.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 08/07/2018
 *
 * @author lyrold
 */
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {this.userService = userService;}

  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    return ResponseEntity.ok(userService.get());
  }

}
