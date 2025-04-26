package com.own.identity_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String title;
    private LocalDateTime dateTime;
    private String providerId;
}
