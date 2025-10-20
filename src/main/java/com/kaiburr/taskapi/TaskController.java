package com.kaiburr.taskapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/tasks") // All URLs will start with /api/tasks
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // PUT /api/tasks (Create a new task)
    @PutMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        if (isMalicious(task.getCommand())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new ArrayList<>());
        }
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    // GET /api/tasks  (Get all tasks)
    // GET /api/tasks?id={id} (Get one task by ID)
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Task> task = taskRepository.findById(id);
            if (task.isPresent()) {
                return new ResponseEntity<>(List.of(task.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            List<Task> tasks = taskRepository.findAll();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    // GET /api/tasks/findByName?name={searchString}
    @GetMapping("/findByName")
    public ResponseEntity<List<Task>> findTasksByName(@RequestParam String name) {
        List<Task> tasks = taskRepository.findByNameContaining(name);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
    }

    // DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable String id) {
        if (!taskRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PUT /api/tasks/run/{id} (Run the task)
    @PutMapping("/run/{id}")
    public ResponseEntity<Task> runTask(@PathVariable String id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task task = taskOptional.get();
        TaskExecution execution = new TaskExecution();
        execution.setStartTime(new Date());

        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            // This is for Windows. For Linux/macOS, use: new String[]{"/bin/sh", "-c", task.getCommand()}
            processBuilder.command("cmd.exe", "/c", task.getCommand());
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    output.append("ERROR: ").append(line).append("\n");
                }
            }

            if (!process.waitFor(1, TimeUnit.MINUTES)) {
                process.destroy();
                output.append("\nExecution timed out.");
            }
            execution.setOutput(output.toString());
        
        } catch (Exception e) {
            execution.setOutput("Execution failed: " + e.getMessage());
        } finally {
            execution.setEndTime(new Date());
        }
        
        task.getTaskExecutions().add(execution);
        Task updatedTask = taskRepository.save(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
    
    // Simple security check
    private boolean isMalicious(String command) {
        if (command == null || command.isEmpty()) return false;
        String lower = command.toLowerCase();
        // This is a very basic check
        return lower.contains("rm ") || lower.contains("sudo") || lower.contains("mv ");
    }
}