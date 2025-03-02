package com.auth.proyectofinal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.proyectofinal.model.UserSec;

@Repository
public interface UserRepository extends JpaRepository<UserSec, Long> {
	Optional<UserSec> findByUsername(String username);
}
