package com.test.workforcemgmt.model;

import com.test.workforcemgmt.enums.Priority;
import com.test.workforcemgmt.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Status status = Status.ACTIVE;
    private Priority priority = Priority.MEDIUM;
    private Long assignedStaffId;
    private List<String> comments = new ArrayList<>();
    private List<String> activityLogs = new ArrayList<>();
}
