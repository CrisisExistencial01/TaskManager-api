package TaskManager.TaskManager.service;

import TaskManager.TaskManager.dto.Task.TaskRequestDTO;
import TaskManager.TaskManager.exception.ResourceNotFoundException;
import TaskManager.TaskManager.model.Task;
import TaskManager.TaskManager.model.User;
import TaskManager.TaskManager.repository.TaskRepository;
import TaskManager.TaskManager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public Task createTask(Long userId, TaskRequestDTO taskRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Task task = taskRequest.toEntity();
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getAllUserTasks(Long userId) {
        boolean user = userRepository.existsById(userId);
        if (!user) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return taskRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id " + taskId + " for user " + userId));
        taskRepository.delete(task);
    }

}
