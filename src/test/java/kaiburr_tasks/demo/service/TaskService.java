package kaiburr_tasks.demo.service;

import kaiburr_tasks.demo.model.Task;
import kaiburr_tasks.demo.model.TaskExecution;
import kaiburr_tasks.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Dependency injection via constructor
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // --- CRUD Operations ---

    /**
     * Retrieves all tasks from the database.
     */
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieves a single task by its ID.
     */
    public Optional<Task> findTaskById(String id) {
        return taskRepository.findById(id);
    }

    /**
     * Saves a new task or updates an existing one, performing security validation first.
     * @throws IllegalArgumentException if the command is deemed unsafe.
     */
    public Task saveTask(Task task) throws IllegalArgumentException {
        if (!isValidCommand(task.getCommand())) {
            throw new IllegalArgumentException("Command contains unsafe/malicious code (e.g., command chaining or dangerous keywords).");
        }
        // Spring Data's save handles both creation (if ID is null) and update (if ID exists)
        return taskRepository.save(task);
    }

    /**
     * Deletes a task by ID.
     */
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    /**
     * Searches for tasks whose name contains the specified string (case-insensitive).
     */
    public List<Task> findTasksByName(String name) {
        // This method relies on the custom query defined in the TaskRepository interface
        return taskRepository.findByNameContainingIgnoreCase(name);
    }

    // --- Execution and Validation Logic ---

    /**
     * Executes the shell command associated with a task and records the execution details.
     * NOTE: This executes the command locally. In a real Kubernetes environment, this would
     * involve interacting with the Kubernetes API to schedule a Job/Pod.
     */
    public Optional<Task> executeTask(String taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);

        if (taskOpt.isEmpty()) {
            return Optional.empty(); // Task not found
        }

        Task task = taskOpt.get();
        TaskExecution execution = new TaskExecution();
        execution.setStartTime(LocalDateTime.now());
        
        // Execute the command using the system's shell
        try {
            // Using "sh -c" ensures the command is executed as a shell command (useful for piping/redirection)
            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", task.getCommand()});
            
            // Read output stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            int exitCode = process.waitFor();
            
            if (exitCode != 0) {
                 // If execution failed, append the error stream content
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                 String errorLine;
                 while ((errorLine = errorReader.readLine()) != null) {
                     output.append("ERROR: ").append(errorLine).append("\n");
                 }
                 output.append("Command failed with exit code: ").append(exitCode);
            }
            
            execution.setEndTime(LocalDateTime.now());
            execution.setOutput(output.toString().trim());

        } catch (Exception e) {
            execution.setEndTime(LocalDateTime.now());
            execution.setOutput("Execution Error: " + e.getMessage());
        }

        // Add the new execution record and save the updated task object
        task.getTaskExecutions().add(execution);
        return Optional.of(taskRepository.save(task));
    }

    /**
     * Simplistic command validation to prevent malicious code injection.
     * In a production system, this would be much more rigorous.
     */
    private boolean isValidCommand(String command) {
        // Block command chaining using shell operators (&&, ; , |)
        if (command.contains("&&") || command.contains(";") || command.contains("|")) {
             return false;
        }
        // Block dangerous Linux commands (e.g., deleting files recursively)
        if (command.toLowerCase().contains("rm -rf") || command.toLowerCase().contains("kubectl delete")) {
            return false;
        }
        // Allow basic commands like echo, ls, pwd, cat
        return true;
    }
}
