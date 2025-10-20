package kaiburr_tasks.demo.model; // ADJUSTED PACKAGE

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "tasks") 
public class Task {
    @Id 
    private String id;
    private String name;
    private String owner;
    private String command;
    private List<TaskExecution> taskExecutions = new ArrayList<>();

    // Constructors
    public Task() {}

    // --- Getters and Setters (Use Lombok or manually add them) ---
    // Example:
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    // ... add getters/setters for name, owner, command, and taskExecutions
}