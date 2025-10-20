package kaiburr_tasks.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExecution {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String output;

    // Optional manual setters if Lombok is not recognized
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
