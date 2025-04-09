package dev.renancmd.MyOrganizer.controller;

import dev.renancmd.MyOrganizer.dto.TaskRequestDTO;
import dev.renancmd.MyOrganizer.dto.TaskResponseDTO;
import dev.renancmd.MyOrganizer.model.User;
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
        return ResponseEntity.ok("Task atualizada com sucesso!");
    }


}

