package TaskManager.TaskManager.service;

import TaskManager.TaskManager.dto.User.UserRequestDTO;
import TaskManager.TaskManager.exception.DuplicateDataException;
import TaskManager.TaskManager.exception.ResourceNotFoundException;
import TaskManager.TaskManager.model.User;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public User registerUser(UserRequestDTO userRequest) {
        return userService.createUser(userRequest);
    }

    public void login(UserRequestDTO userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid Email or password");
        }
    }
}
