package TaskManager.TaskManager.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String email;
    private String id;
    private List<String> roles;

    public JwtResponseDTO(String token, String email, String id, List<String> roles) {
        this.token = token;
        this.email = email;
        this.id = id;
        this.roles = roles;
    }
}
