# Kaiburr Task 1: Java REST API

This is a Spring Boot application that provides a REST API for managing "Task" objects. It uses a MongoDB database to store the task data.

This project fulfills all requirements for Task 1, including endpoints for creating, finding, running, and deleting tasks.

---

## How to Run the Application

### Prerequisites
* Java 17 (or newer)
* Apache Maven
* MongoDB Server (running on localhost:27017)

### Instructions
1.  Make sure your MongoDB server is running.
2.  Open a command prompt or terminal and navigate to the root folder of this project (the folder that contains `pom.xml`).
3.  Run the application using the following Maven command:
    ```bash
    mvn spring-boot:run
    ```
4.  The API server will start and be available at `http://localhost:8080`.

---

## API Endpoint Screenshots

**Note:** All screenshots include my name and the current date/time as required by the submission rules.

### 1. Create Task: `PUT /api/tasks`
*This endpoint creates a new task in the database.*

(Leave this space blank for now)

### 2. Run Task: `PUT /api/tasks/run/{id}`
*This endpoint executes the command for a specific task.*

(Leave this space blank for now)

### 3. Get Task by ID (View Output): `GET /api/tasks?id={id}`
*This endpoint shows the task details, including the output from the command run.*

(Leave this space blank for now)

### 4. Get All Tasks: `GET /api/tasks`
*This shows a list of all tasks in the database.*

(Leave this space blank for now)

### 5. Find Task by Name: `GET /api/tasks/findByName`
*This searches for tasks containing the provided name string.*

(Leave this space blank for now)

### 6. Delete Task: `DELETE /api/tasks/{id}`
*This deletes the task from the database.*

