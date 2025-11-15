package TaskManager.TaskManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO <DTO> {

    private boolean success;
    private String message;
    private DTO data;

}
