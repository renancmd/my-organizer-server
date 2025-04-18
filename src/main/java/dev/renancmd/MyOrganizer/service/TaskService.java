package dev.renancmd.MyOrganizer.service;

import dev.renancmd.MyOrganizer.dto.TaskRequestDTO;
import dev.renancmd.MyOrganizer.dto.TaskResponseDTO;
import dev.renancmd.MyOrganizer.model.Task;
import dev.renancmd.MyOrganizer.model.User;
import dev.renancmd.MyOrganizer.repository.TaskRepository;
import dev.renancmd.MyOrganizer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void createTask(String email, TaskRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Task task = Task.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .date(dto.getDate())
                .user(user)
                .build();

        taskRepository.save(task);
    }

    public List<TaskResponseDTO> getTasks(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user)
                .stream()
                .map(task -> TaskResponseDTO.builder()
                        .id(task.getId())
                        .done(task.isDone())
                        .name(task.getName())
                        .description(task.getDescription())
                        .date(task.getDate())
                        .build())
                .toList();
    }

    public void updateTask(String email, Long taskId, TaskRequestDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User doesn't belong to this task");
        }

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setDate(dto.getDate());

        taskRepository.save(task);
    }

    public void deleteTask(String email, Long taskId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User doesn't belong to this task");
        }

        taskRepository.delete(task);
    }

    public TaskResponseDTO getTaskById(String email, Long taskId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task não encontrada."));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Você não tem permissão para ver essa tarefa.");
        }

        return TaskResponseDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .date(task.getDate())
                .build();
    }

    public void updateTaskStatus(String email, Long taskId, boolean done) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task não encontrada."));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Você não tem permissão para alterar essa tarefa.");
        }

        task.setDone(done);
        taskRepository.save(task);
    }



}
