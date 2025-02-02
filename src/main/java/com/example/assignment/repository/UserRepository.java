package com.example.assignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assignment.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>  {
	Optional<UserEntity> findByEmail(String email);
}
