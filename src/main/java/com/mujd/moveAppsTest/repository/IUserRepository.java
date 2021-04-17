package com.mujd.moveAppsTest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mujd.moveAppsTest.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public List<User> findByIsActive(Boolean isActive);
}
