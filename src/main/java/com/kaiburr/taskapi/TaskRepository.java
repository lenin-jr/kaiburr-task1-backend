package com.kaiburr.taskapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

// This interface talks to the database for you
public interface TaskRepository extends MongoRepository<Task, String> {

    // This one line automatically creates a "find by name" search
    List<Task> findByNameContaining(String name);
}