package com.auth.proyectofinal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.proyectofinal.model.Role;
import com.auth.proyectofinal.model.UserSec;
import com.auth.proyectofinal.model.dto.AuthLoginRequestDTO;
import com.auth.proyectofinal.model.dto.AuthLoginResponseDTO;
import com.auth.proyectofinal.model.dto.AuthRegisterRequestDTO;
import com.auth.proyectofinal.model.dto.AuthRegisterResponseDTO;
import com.auth.proyectofinal.repository.RoleRepository;
import com.auth.proyectofinal.repository.UserRepository;
import com.auth.proyectofinal.utils.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String DEFAULT_ROLE = "USER";
	
	public AuthLoginResponseDTO login(AuthLoginRequestDTO userRequest) {
		String username = userRequest.username();
		String password = userRequest.password();
		
		Authentication authentication = this.authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtUtils.createToken(authentication);
		AuthLoginResponseDTO authResponseDTO = new AuthLoginResponseDTO(username, "Login successful", accessToken);
		return authResponseDTO;
	}
	
	public Authentication authenticate(String username, String password) {
		UserDetails userDetails = this.loadUserByUsername(username);
		if(userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword()))
			throw new BadCredentialsException("Invalid username or password");
		return new UsernamePasswordAuthenticationToken(username, password,userDetails.getAuthorities());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserSec userSec = userRepository.findByUsername(username)
				.orElseThrow(()->new BadCredentialsException("Invalid username or password"));
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<SimpleGrantedAuthority>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(userSec.getRole().getRoleName())));
		
		userSec.getRole().getPermissionSet().stream()
			.forEach(permission-> authorityList.add(new SimpleGrantedAuthority(permission.getPermission())));

		return new User(userSec.getUsername(), userSec.getPassword(),
				userSec.isEnabled(),userSec.isAccountNonExpired(),userSec.isCredentialNonExpired(), userSec.isAccountNonLocked(),
				authorityList);
	}
	
	public AuthRegisterResponseDTO register(AuthRegisterRequestDTO request) {
		Optional<UserSec> userFound = userRepository.findByUsername(request.username());
		String role = request.role();
		if(request.role() == null || request.role().isEmpty()) 
			role = DEFAULT_ROLE;
		
		Optional<Role> roleFound = roleRepository.findById(role);
		if(userFound.isPresent())
			return new AuthRegisterResponseDTO("Error, A user with that username: "+request.username() +" already exists",null);
		else if(roleFound.isEmpty())
			return new AuthRegisterResponseDTO("Error, role: "+request.role()+ " is invalid",null);
		UserSec userSec = new UserSec();
		userSec.setUsername(request.username());
		userSec.setPassword(this.encriptPassword(request.password()));
		userSec.setRole(roleFound.get());
		userRepository.save(userSec);
		return new AuthRegisterResponseDTO("User: "+request.username()+" has registered successfully",role);
	}
	
	
	private String encriptPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
	
}
