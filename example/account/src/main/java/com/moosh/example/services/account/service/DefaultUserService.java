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

    private static final String A_USER_ID = UUID.randomUUID().toString();

    @Override
    public List<User> get() {
        return Arrays.asList(
                new User(A_USER_ID, "Jeanette", "Doe"),
                new User(A_USER_ID, "John", "Doe")
        );
    }
}
