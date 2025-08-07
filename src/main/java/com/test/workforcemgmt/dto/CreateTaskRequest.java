package com.test.workforcemgmt.dto;

import com.test.workforcemgmt.enums.Priority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Priority priority;
    private Long staffId;
}
