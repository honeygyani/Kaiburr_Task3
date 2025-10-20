package kaiburr_tasks.demo.repository; // ADJUSTED PACKAGE

import kaiburr_tasks.demo.model.Task; // Import Task model
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    
    // Query for finding tasks by name (case-insensitive search)
    List<Task> findByNameContainingIgnoreCase(String name);
}