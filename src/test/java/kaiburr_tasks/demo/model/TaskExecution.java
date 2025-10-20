package kaiburr_tasks.demo.model; // ADJUSTED PACKAGE

import java.time.LocalDateTime;

public class TaskExecution {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String output;

    // Constructors (required for JSON serialization/deserialization)
    public TaskExecution() {}

    // --- Getters and Setters (Use Lombok or manually add them) ---
    // Example:
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    // ... add getters/setters for endTime and output
}