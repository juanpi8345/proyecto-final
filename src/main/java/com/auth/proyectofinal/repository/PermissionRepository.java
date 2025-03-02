package com.auth.proyectofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.proyectofinal.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

}
