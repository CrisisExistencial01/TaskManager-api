package TaskManager.TaskManager.service;

import TaskManager.TaskManager.dto.Auth.JwtResponseDTO;
import TaskManager.TaskManager.dto.User.UserRequestDTO;
import TaskManager.TaskManager.exception.DuplicateDataException;
import TaskManager.TaskManager.exception.ResourceNotFoundException;
import TaskManager.TaskManager.model.User;
import TaskManager.TaskManager.security.JwtUtils;
import TaskManager.TaskManager.service.Security.ApplicationUserDetailsService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ApplicationUserDetailsService userDetailsService;

    public AuthService(
        UserService userService,
        AuthenticationManager authenticationManager,
        JwtUtils jwtUtils,
        ApplicationUserDetailsService userDetailsService
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public User registerUser(UserRequestDTO userRequest) {
        return userService.createUser(userRequest);
    }

    public JwtResponseDTO login(UserRequestDTO userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userRequest.getEmail(),
                    userRequest.getPassword()
                )
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();
                String jwt = jwtUtils.generateJwtToken(authentication);

                User user = userService.getUserByEmail(userRequest.getEmail());

                List<String> roles = userDetails
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

                return new JwtResponseDTO(
                    jwt,
                    userDetails.getUsername(),
                    user.getId().toString(),
                    roles
                );
            }
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid Email or password");
        }
        return null;
    }
}
