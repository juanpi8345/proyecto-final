package com.auth.proyectofinal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor 
@NoArgsConstructor
@Table(name = "permissions")
public class Permission {
	@Id
	private String permission;
	
    @PrePersist
    @PreUpdate
    private void toUpperCase() {
        if (permission != null) {
        	permission = permission.toUpperCase();
        }
    }
}
