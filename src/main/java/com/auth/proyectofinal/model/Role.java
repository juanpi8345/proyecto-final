package com.auth.proyectofinal.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "roles")
public class Role {
	@Id
	@Column(name = "role")
	private String roleName;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="role_permissions",joinColumns = @JoinColumn(name="role_name")
	,inverseJoinColumns = @JoinColumn(name="permission_name"))
	private Set<Permission> permissionSet = new HashSet<Permission>();
	
    @PrePersist
    @PreUpdate
    private void toUpperCase() {
        if (roleName != null) {
        	roleName = roleName.toUpperCase();
        }
    }
}
