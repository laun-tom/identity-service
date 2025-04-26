package com.own.identity_service.controller;

import com.own.identity_service.domain.Task;
import com.own.identity_service.dto.TaskDto;
import com.own.identity_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("")
    public Task createTask(@RequestBody TaskDto taskDto) {
        return this.taskService.save(taskDto);
    }
}
