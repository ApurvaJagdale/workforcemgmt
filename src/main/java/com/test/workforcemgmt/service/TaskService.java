package com.test.workforcemgmt.service;

import com.test.workforcemgmt.dto.CreateTaskRequest;
import com.test.workforcemgmt.dto.TaskDto;
import com.test.workforcemgmt.enums.Priority;
import com.test.workforcemgmt.enums.Status;
import com.test.workforcemgmt.mapper.TaskMapper;
import com.test.workforcemgmt.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskMapper taskMapper;
    private final Map<Long, Task> taskMap = new HashMap<>();
    private final AtomicLong taskIdCounter = new AtomicLong(1);

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public TaskDto createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setId(taskIdCounter.getAndIncrement());
        task.setTitle(request.getTitle());
        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setAssignedStaffId(request.getStaffId());
        task.getActivityLogs().add("Task created");
        taskMap.put(task.getId(), task);
        return taskMapper.toDto(task);
    }

    public void reassignTask(Long taskId, Long newStaffId) {
        Task oldTask = taskMap.get(taskId);
        oldTask.setStatus(Status.CANCELLED);
        oldTask.getActivityLogs().add("Task reassigned to staff ID " + newStaffId);

        Task newTask = new Task(
                taskIdCounter.getAndIncrement(),
                oldTask.getTitle(),
                LocalDate.now(),
                oldTask.getDueDate(),
                Status.ACTIVE,
                oldTask.getPriority(),
                newStaffId,
                new ArrayList<>(),
                new ArrayList<>(List.of("Task reassigned from task ID " + oldTask.getId()))
        );
        taskMap.put(newTask.getId(), newTask);
    }

    public List<TaskDto> getTasksByDateRange(LocalDate start, LocalDate end) {
        return taskMap.values().stream()
                .filter(task -> task.getStatus() != Status.CANCELLED)
                .filter(task -> (task.getStartDate().isAfter(start.minusDays(1)) && task.getStartDate().isBefore(end.plusDays(1)))
                        || (task.getStartDate().isBefore(start) && task.getStatus() == Status.ACTIVE))
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateTaskPriority(Long id, Priority priority) {
        Task task = taskMap.get(id);
        task.setPriority(priority);
        task.getActivityLogs().add("Priority updated to " + priority);
    }

    public List<TaskDto> getTasksByPriority(Priority priority) {
        return taskMap.values().stream()
                .filter(task -> task.getPriority() == priority && task.getStatus() != Status.CANCELLED)
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public void addComment(Long id, String comment) {
        Task task = taskMap.get(id);
        task.getComments().add(comment);
        task.getActivityLogs().add("Comment added: " + comment);
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskMap.get(id);
        return taskMapper.toDto(taskMap.get(id));
    }

    /*private TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStartDate(task.getStartDate());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setAssignedStaffId(task.getAssignedStaffId());
        dto.setComments(task.getComments());
        dto.setActivityLogs(task.getActivityLogs());
        return dto;
    }*/
}
