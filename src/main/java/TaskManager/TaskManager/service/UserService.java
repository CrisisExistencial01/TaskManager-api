package TaskManager.TaskManager.service;

import TaskManager.TaskManager.dto.User.UserRequestDTO;
import TaskManager.TaskManager.exception.DuplicateDataException;
import TaskManager.TaskManager.exception.ResourceNotFoundException;
import TaskManager.TaskManager.model.User;
import TaskManager.TaskManager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        User user = userRequest.toEntity();
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
