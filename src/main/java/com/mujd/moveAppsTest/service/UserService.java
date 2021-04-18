package com.mujd.moveAppsTest.service;

import java.util.List;
import java.util.Optional;

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
	public List<User> listUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	@Transactional
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	@Transactional
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		return (User) userRepository.save(user);
	}

	@Override
	@Transactional
	public User findByEmail(User user) {
		return userRepository.findByEmail(user.getEmail());
	}

	public List<User> findByIsActive(Boolean isActive) {
		return userRepository.findByIsActive(isActive);
	}
}
