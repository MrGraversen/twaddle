package io.graversen.twaddle.data.repository.jpa;

import io.graversen.twaddle.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUserId(String userId);
}
