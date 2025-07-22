package TaskManager.TaskManager.controller;

import TaskManager.TaskManager.dto.Task.TaskRequestDTO;
import TaskManager.TaskManager.dto.Task.TaskResponseDTO;
import TaskManager.TaskManager.model.Task;
import TaskManager.TaskManager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("{userId}/tasks")
    public ResponseEntity<TaskResponseDTO> createTask(@PathVariable Long userId,
                                                      @RequestBody TaskRequestDTO taskRequest) {
        Task createdTask = taskService.createTask(userId, taskRequest);
        TaskResponseDTO dto = TaskResponseDTO.fromEntity(createdTask);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getAllUserTasks(@PathVariable Long userId) {
        List<Task> tasks = taskService.getAllUserTasks(userId);
        List<TaskResponseDTO> dto_list = TaskResponseDTO.fromEntities(tasks);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto_list);
    }

    @DeleteMapping("{userId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long userId,
                                           @PathVariable Long taskId) {
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }

}
