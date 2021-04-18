package com.mujd.moveAppsTest.service;

import java.util.List;
import java.util.Optional;

import com.mujd.moveAppsTest.model.User;

public interface IUserService {
	public List<User> listUsers();

	public void deleteUserById(long id);
	
	public void delete(User user);

	public void save(User user);

	public Optional<User> findById(Long id);

	public User updateUser(User user);

	public User findByEmail(User user);

	public List<User> findByIsActive(Boolean isActive);
}
