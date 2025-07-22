package TaskManager.TaskManager.dto.Task;

import TaskManager.TaskManager.model.Task;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;

    public static TaskResponseDTO fromEntity(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }
    public static List<TaskResponseDTO> fromEntities(List<Task> entities) {
        return entities.stream()
                .map(TaskResponseDTO::fromEntity)
                .toList();
    }
}
