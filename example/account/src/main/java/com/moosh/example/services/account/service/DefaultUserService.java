package com.moosh.example.services.account.service;

import com.moosh.example.services.account.dto.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created on 08/07/2018
 *
 * @author lyrold
 */
@Service
public class DefaultUserService implements UserService {

  @Override
  public List<User> get() {

    User jeannette = new User();
    jeannette.setId(UUID.randomUUID().toString());
    jeannette.setFirstName("Jeannette");
    jeannette.setLastName("Doe");

    User john = new User();
    john.setId(UUID.randomUUID().toString());
    john.setFirstName("John");
    john.setLastName("Doe");

    return Arrays.asList(jeannette, john);
  }
}
