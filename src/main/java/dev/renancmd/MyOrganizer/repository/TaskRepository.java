package dev.renancmd.MyOrganizer.repository;

import dev.renancmd.MyOrganizer.model.Task;
import dev.renancmd.MyOrganizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
