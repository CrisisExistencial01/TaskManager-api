package TaskManager.TaskManager.controller;

import TaskManager.TaskManager.dto.User.UserRequestDTO;
import TaskManager.TaskManager.model.User;
import TaskManager.TaskManager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO userRequest) {
        User created = userService.createUser(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        Map<String, String> response = new HashMap<>();
        return ResponseEntity.noContent().build();
    }
}
