package com.own.identity_service.service;

import com.own.identity_service.domain.Task;
import com.own.identity_service.domain.User;
import com.own.identity_service.dto.TaskDto;
import com.own.identity_service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public Task save(TaskDto taskDto) {
        User user = this.userService.getUserByProviderId(taskDto.getProviderId());
        Task task = Task.builder()
                .title(taskDto.getTitle())
                .datetime(taskDto.getDateTime())
                .user(user)
                .build();
        return taskRepository.save(task);
    }
}
