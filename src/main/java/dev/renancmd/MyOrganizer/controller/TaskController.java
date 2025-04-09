package dev.renancmd.MyOrganizer.controller;

import dev.renancmd.MyOrganizer.dto.TaskRequestDTO;
import dev.renancmd.MyOrganizer.dto.TaskResponseDTO;
import dev.renancmd.MyOrganizer.dto.UpdateTaskStatusDTO;
import dev.renancmd.MyOrganizer.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskRequestDTO dto, Authentication authentication) {
        String email = authentication.getName();
        taskService.createTask(email, dto);
        return ResponseEntity.ok("Task created!");
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> listTasks(Authentication authentication) {
        String email = authentication.getName();
        List<TaskResponseDTO> tasks = taskService.getTasks(email);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        taskService.updateTask(email, id, dto);
        return ResponseEntity.ok("Task updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        taskService.deleteTask(email, id);
        return ResponseEntity.ok("Task deleted successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        TaskResponseDTO task = taskService.getTaskById(email, id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable Long id,
            @RequestBody UpdateTaskStatusDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        taskService.updateTaskStatus(email, id, dto.isDone());
        return ResponseEntity.ok("Status da task atualizado com sucesso!");
    }


}

