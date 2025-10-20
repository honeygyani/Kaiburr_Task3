package kaiburr_tasks.demo.controller;

import kaiburr_tasks.demo.model.Task;
import kaiburr_tasks.demo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks") // Base URL for all endpoints in this controller
public class TaskController {

    private final TaskService taskService;

    // Inject the TaskService
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * GET tasks:
     * - If 'id' parameter is present: Returns a single task (or 404).
     * - If no parameters: Returns all tasks.
     * URL: /api/tasks?id={taskId} OR /api/tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasksOrTaskById(@RequestParam(required = false) String id) {
        if (id != null) {
            // Find by ID case
            return taskService.findTaskById(id)
                    // Wrap the single Task in a List for consistent response type
                    .map(task -> ResponseEntity.ok(List.of(task))) 
                    .orElse(ResponseEntity.notFound().build()); // 404
        }
        // Find all tasks case
        return ResponseEntity.ok(taskService.findAllTasks());
    }
    
    /**
     * PUT a task (Create or Update).
     * The task object is passed in the request body.
     * Validation happens in the TaskService layer.
     * URL: /api/tasks
     */
    @PutMapping
    public ResponseEntity<?> createOrUpdateTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.saveTask(task);
            return new ResponseEntity<>(savedTask, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // Catches the security validation error
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }

    /**
     * DELETE a task.
     * URL: /api/tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    
    /**
     * GET (find) tasks by name.
     * URL: /api/tasks/search?name={searchString}
     */
    @GetMapping("/search")
    public ResponseEntity<List<Task>> findTasksByName(@RequestParam String name) {
        List<Task> tasks = taskService.findTasksByName(name);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 if nothing found
        }
        return ResponseEntity.ok(tasks);
    }

    /**
     * PUT a TaskExecution (Execute a shell command).
     * URL: /api/tasks/execute/{taskId}
     */
    @PutMapping("/execute/{taskId}")
    public ResponseEntity<Task> executeTask(@PathVariable String taskId) {
        return taskService.executeTask(taskId)
                .map(ResponseEntity::ok) // 200 OK with the updated task
                .orElse(ResponseEntity.notFound().build()); // 404 if task ID not found
    }
}
