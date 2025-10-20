package kaiburr_tasks.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String name;
    private String owner;
    private String command;

    // list of TaskExecution objects
    private List<TaskExecution> taskExecutions = new ArrayList<>();

    // Optional: manual getters if Lombok is not working properly
    public String getCommand() {
        return command;
    }

    public List<TaskExecution> getTaskExecutions() {
        return taskExecutions;
    }

    public void setTaskExecutions(List<TaskExecution> taskExecutions) {
        this.taskExecutions = taskExecutions;
    }
}
