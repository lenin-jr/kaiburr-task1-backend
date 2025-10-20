package com.kaiburr.taskapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is the main file that starts your whole app
@SpringBootApplication
public class TaskApiApplication {

    public static void main(String[] args) {
        // This line "boots" (starts) your app
        SpringApplication.run(TaskApiApplication.class, args);
    }
}