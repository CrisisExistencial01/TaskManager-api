package TaskManager.TaskManager.controller;

import TaskManager.TaskManager.dto.User.UserRequestDTO;
import TaskManager.TaskManager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userRequest){
        authService.registerUser(userRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequest){
        authService.login(userRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Usuario autenticado exitosamente");
    }

}
