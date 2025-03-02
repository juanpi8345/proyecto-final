package com.auth.proyectofinal.service;

import java.util.List;

import com.auth.proyectofinal.model.UserSec;

public interface UserSecService {
	List<UserSec> findAll();
	UserSec getUser(Long userId);
	void deleteUser(Long userId);

}
