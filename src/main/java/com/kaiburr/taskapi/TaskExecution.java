package com.kaiburr.taskapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

// This is the small object for a single command run
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskExecution {
    private Date startTime;
    private Date endTime;
    private String output;
}