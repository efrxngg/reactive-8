package edu.spring.reactive.service;

import edu.spring.reactive.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    List<User> getAllUsers(Pageable pageable);

}
