package com.own.identity_service.mapper;

import com.own.identity_service.domain.Task;
import com.own.identity_service.dto.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);
}
