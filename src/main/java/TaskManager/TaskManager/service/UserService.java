package TaskManager.TaskManager.service;

import TaskManager.TaskManager.dto.User.UserRequestDTO;
import TaskManager.TaskManager.exception.DuplicateDataException;
import TaskManager.TaskManager.exception.ResourceNotFoundException;
import TaskManager.TaskManager.model.Role;
import TaskManager.TaskManager.model.User;
import TaskManager.TaskManager.repository.UserRepository;
import TaskManager.TaskManager.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public User createUser(UserRequestDTO userRequest) {
        String username = userRequest.getUsername();
        String correo = userRequest.getEmail();
        if(userRepository.findByUsername(username).isPresent()){
            throw new DuplicateDataException("User", "username", username);
        } else if (userRepository.findByEmail(correo).isPresent()) {
            throw new DuplicateDataException("User", "email", correo);
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: USER"));

        User user = userRequest.toEntity();
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);

    }
}
