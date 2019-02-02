package io.graversen.twaddle.data.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(name = "user_id")
    private String userId;

    @NonNull
    @Column(name = "username")
    private String username;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}