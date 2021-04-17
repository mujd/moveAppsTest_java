package com.mujd.moveAppsTest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mujd.moveAppsTest.model.User;
import com.mujd.moveAppsTest.repository.IUserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserRepository userRepository;

	@Override
	@Transactional
	public User findByEmail(User user) {
		return userRepository.findByEmail(user.getEmail());
	}

	// @Override
	// @Transactional
	// public User findByIsActive(User user) {
	// return userRepository.findByIsActive(user.getIsActive());
	// }

	public List<User> findByIsActive(Boolean isActive) {
		return userRepository.findByIsActive(isActive);
	}

	// @Override
	// @Transactional
	// public List<User> selectAllUsersByIsActive(Boolean isActive) {
	// return userRepository.selectAllUsersByIsActive(isActive);
	// }

}
