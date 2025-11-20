package com.CourseWorld.CourseWorld.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CourseWorld.CourseWorld.Component.PasswordResetToken;
import com.CourseWorld.CourseWorld.Component.User;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user);
}