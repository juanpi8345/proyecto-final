package com.auth.proyectofinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.proyectofinal.exception.ResourceNotFoundException;
import com.auth.proyectofinal.model.UserSec;
import com.auth.proyectofinal.repository.UserRepository;
import com.auth.proyectofinal.service.UserSecService;

@Service
public class UserSecServiceImpl implements UserSecService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserSec> findAll() {
		return userRepository.findAll();
	}

	@Override
	public UserSec getUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User with id: "+userId+ " not found"));
	}

	@Override
	public void deleteUser(Long userId) {
		UserSec user = userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User with id: "+userId+ " not found"));

		user.setEnabled(false);
		user.setAccountNonExpired(false);
		user.setCredentialNonExpired(false);
		user.setAccountNonLocked(false);
		userRepository.save(user);
	}
}
