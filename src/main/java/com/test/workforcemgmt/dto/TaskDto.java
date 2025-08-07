package com.test.workforcemgmt.dto;

import com.test.workforcemgmt.enums.Priority;
import com.test.workforcemgmt.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Status status;
    private Priority priority;
    private Long assignedStaffId;
    private List<String> comments;
    private List<String> activityLogs;
}
