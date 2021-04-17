package com.mujd.moveAppsTest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "created")
	private Date created;

	@Column(name = "updated")
	private Date updated;

	@Column(name = "last_login")
	private Date last_login;

	@Column(name = "token")
	private String token;

	@Column(name = "isActive")
	private Boolean isActive = true;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Roles role = Roles.SUPER_ADMIN;

	@PrePersist
	public void prePersist() {
		created = new Date();
		updated = new Date();
		last_login = new Date();
	}

	public User() {
		super();
	}

	public User(long id, String email, String password, Date created, Date updated, Date last_login, String token,
			Boolean isActive, Roles role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.created = created;
		this.updated = updated;
		this.last_login = last_login;
		this.token = token;
		this.isActive = isActive;
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public enum Roles {
		SUPER_ADMIN, EMPLOYEE, SUPERVISOR,
	}
}
