package com.kaiburr.taskapi;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

// This is your main database object
@Data
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String name;
    private String owner;
    private String command;
    
    // We start with an empty list so it's never null
    private List<TaskExecution> taskExecutions = new ArrayList<>(); 
}