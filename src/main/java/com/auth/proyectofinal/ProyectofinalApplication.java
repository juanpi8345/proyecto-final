package com.auth.proyectofinal;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth.proyectofinal.model.Permission;
import com.auth.proyectofinal.model.Role;
import com.auth.proyectofinal.model.UserSec;
import com.auth.proyectofinal.repository.UserRepository;

@SpringBootApplication
public class ProyectofinalApplication implements CommandLineRunner {	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${security.admin.password}")
	String adminPassword;

	public static void main(String[] args) {
		SpringApplication.run(ProyectofinalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(userRepository.findAll().isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
			UserSec user = new UserSec();
			Role role = new Role();
			Set<Permission> permissions = new HashSet<Permission>();
			permissions.add(new Permission("CREATE"));
			permissions.add(new Permission("READ"));
			permissions.add(new Permission("DELETE"));
			permissions.add(new Permission("UPDATE"));
			role.setPermissionSet(permissions);
			role.setRoleName("ADMIN");
			user.setUsername("Juan");
			user.setPassword(passwordEncoder.encode(adminPassword));
			user.setRole(role);
			userRepository.save(user);
		}
	}
}
