package TaskManager.TaskManager.dto.User;

import TaskManager.TaskManager.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;

    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static List<UserResponseDTO> fromEntities(List<User> users) {
        return users.stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }
}
