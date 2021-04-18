package com.mujd.moveAppsTest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mujd.moveAppsTest.exception.ResourceBadRequestException;
import com.mujd.moveAppsTest.exception.ResourceFoundWithNoContentException;
import com.mujd.moveAppsTest.exception.ResourceNotFoundException;
import com.mujd.moveAppsTest.model.User;
import com.mujd.moveAppsTest.service.IUserService;
import com.mujd.moveAppsTest.validation.EmailValidation;
import com.mujd.moveAppsTest.validation.PasswordValidator;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private IUserService iUserservice;

	private static EmailValidation emailValidation;
	private static PasswordValidator passwordValidator;

	// get users
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUser() throws ResourceFoundWithNoContentException {
		List<User> users = iUserservice.findByIsActive(Boolean.TRUE);
		if (users.isEmpty()) {
			throw new ResourceFoundWithNoContentException("No hay usuarios activos.");
		} else {
			return ResponseEntity.ok().body(users);
		}
	}

	// get user by id
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") String userId)
			throws ResourceNotFoundException {
		User user = iUserservice.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario con el id '" + userId + "' no existe."));

		return ResponseEntity.ok().body(user);
	}

	// save user
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws ResourceBadRequestException {
		User userDB = iUserservice.findByEmail(user);
		emailValidation = new EmailValidation();
		Boolean validEmail = emailValidation.validateEmail(user.getEmail());
		passwordValidator = new PasswordValidator();
		Boolean validPassword = passwordValidator.isValid(user.getPassword());
		if (userDB == null) {
			if (validEmail && validPassword) {

				iUserservice.save(user);
				return ResponseEntity.ok().body(user);
			} else {
				throw new ResourceBadRequestException("El email o passoword ingresado no es válido.");
			}
		} else {
			throw new ResourceBadRequestException("Usuario con el email '" + user.getEmail() + "' ya esta registrado.");
		}
	}

	// update user
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") String userId, @Valid @RequestBody User user)
			throws ResourceNotFoundException {
		User userDB = iUserservice.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario con el id '" + userId + "' no existe."));

		userDB.setPassword(user.getPassword());
		userDB.setIsActive(user.getIsActive());
		userDB.setRole(user.getRole());
		userDB.setUpdated(new Date());
		iUserservice.updateUser(userDB);

		return ResponseEntity.ok(userDB);
	}

	// delete user
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") String userId) throws ResourceNotFoundException {
		User user = iUserservice.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario con el id '" + userId + "' no existe."));
		this.iUserservice.delete(user);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}
}
