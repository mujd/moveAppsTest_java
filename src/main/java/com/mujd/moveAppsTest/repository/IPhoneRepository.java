package com.mujd.moveAppsTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mujd.moveAppsTest.model.Phone;

public interface IPhoneRepository extends JpaRepository<Phone, Long> {

}
