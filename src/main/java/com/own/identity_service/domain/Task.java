package com.own.identity_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task extends BaseEntity {
    @Column(name = "title", length = 100 , nullable = false)
    private String title;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "completed")
    private boolean completed = false;

    @Column(name = "favorite")
    private boolean favorite = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
