package com.CourseWorld.CourseWorld.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CourseWorld.CourseWorld.Component.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	List<User> findByRole(User.Role role);
}
