package com.auth.proyectofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.proyectofinal.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
