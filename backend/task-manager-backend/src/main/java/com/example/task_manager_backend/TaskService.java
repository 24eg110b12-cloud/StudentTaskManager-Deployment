
package com.example.task_manager_backend;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTask(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }

        task.setTitle(task.getTitle().trim());

        if (task.getStatus() == null || task.getStatus().isBlank()) {
            task.setStatus("PENDING");
        }

        return repository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existing = getTask(id);

        if (updatedTask.getTitle() == null || updatedTask.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }

        existing.setTitle(updatedTask.getTitle().trim());
        existing.setDescription(updatedTask.getDescription());
        existing.setDueDate(updatedTask.getDueDate());

        if (updatedTask.getStatus() != null) {
            existing.setStatus(updatedTask.getStatus());
        }

        return repository.save(existing);
    }

    public void deleteTask(Long id) {
        repository.delete(getTask(id));
    }
}
