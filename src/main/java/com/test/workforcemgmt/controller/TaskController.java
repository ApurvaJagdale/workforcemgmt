package com.test.workforcemgmt.controller;

import com.test.workforcemgmt.dto.CreateTaskRequest;
import com.test.workforcemgmt.dto.TaskDto;
import com.test.workforcemgmt.enums.Priority;
import com.test.workforcemgmt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public TaskDto createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<String> reassignTask(@PathVariable Long id, @RequestParam Long newStaffId) {
        taskService.reassignTask(id, newStaffId);
        return ResponseEntity.ok("Task reassigned successfully");
    }

    @GetMapping("/date")
    public List<TaskDto> getTasksByDateRange(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return taskService.getTasksByDateRange(start, end);
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<Void> updatePriority(@PathVariable Long id, @RequestParam Priority priority) {
        taskService.updateTaskPriority(id, priority);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/priority/{priority}")
    public List<TaskDto> getTasksByPriority(@PathVariable Priority priority) {
        return taskService.getTasksByPriority(priority);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody String comment) {
        taskService.addComment(id, comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
}
