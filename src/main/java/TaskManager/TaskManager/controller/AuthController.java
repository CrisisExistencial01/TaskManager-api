package TaskManager.TaskManager.controller;

import TaskManager.TaskManager.dto.Auth.JwtResponseDTO;
import TaskManager.TaskManager.dto.ResponseDTO;
import TaskManager.TaskManager.dto.User.UserRequestDTO;
import TaskManager.TaskManager.dto.User.UserResponseDTO;
import TaskManager.TaskManager.model.User;
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
    public ResponseEntity<ResponseDTO<UserResponseDTO>> register(
        @RequestBody UserRequestDTO userRequest
    ) {
        User user = authService.registerUser(userRequest);
        String message = "User registered successfully";
        UserResponseDTO userResponse = UserResponseDTO.fromEntity(user);

        ResponseDTO<UserResponseDTO> response = ResponseDTO.<
                UserResponseDTO
            >builder()
            .success(true)
            .message(message)
            .data(userResponse)
            .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<JwtResponseDTO>> login(
        @RequestBody UserRequestDTO userRequest
    ) {
        JwtResponseDTO jwtResponse = authService.login(userRequest);

        ResponseDTO<JwtResponseDTO> response = ResponseDTO.<
                JwtResponseDTO
            >builder()
            .success(true)
            .message("Usuario autenticado exitosamente")
            .data(jwtResponse)
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
