package com.mujd.moveAppsTest.service;

import java.util.List;

import com.mujd.moveAppsTest.model.User;

public interface IUserService {

	public User findByEmail(User user);

	public List<User> findByIsActive(Boolean isActive);
}
