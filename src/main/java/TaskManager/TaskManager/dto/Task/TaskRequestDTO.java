package TaskManager.TaskManager.dto.Task;

import TaskManager.TaskManager.dto.interfaces.toEntity;
import TaskManager.TaskManager.model.Task;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskRequestDTO implements toEntity<Task> {
    private String title;
    private String description;
    private String status;
    public Task toEntity(){
        return Task.builder()
                .title(title)
                .description(description)
                .status(status)
                .build();
    }
}
