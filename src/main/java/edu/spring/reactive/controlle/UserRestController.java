package edu.spring.reactive.controlle;

import edu.spring.reactive.entity.User;
import edu.spring.reactive.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Hidden
@RestController
@RequestMapping(name = "/api/v1/users")
public class UserRestController {

    private UserService userService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ok(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> page(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> all() {
        return ok(userService.getAllUsers());
    }
}
