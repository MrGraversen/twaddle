package io.graversen.twaddle.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
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
