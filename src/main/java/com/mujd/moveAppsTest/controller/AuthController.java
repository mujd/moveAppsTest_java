package com.mujd.moveAppsTest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mujd.moveAppsTest.exception.ResourceBadRequestException;
import com.mujd.moveAppsTest.model.User;
import com.mujd.moveAppsTest.service.IUserService;
import com.mujd.moveAppsTest.validation.EmailValidation;
import com.mujd.moveAppsTest.validation.PasswordValidator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private IUserService iUserservice;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private static EmailValidation emailValidation;
	private static PasswordValidator passwordValidator;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// register user
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws ResourceBadRequestException {
		User userDB = iUserservice.findByEmail(user);
		emailValidation = new EmailValidation();
		Boolean validEmail = emailValidation.validateEmail(user.getEmail());
		passwordValidator = new PasswordValidator();
		Boolean validPassword = passwordValidator.isValid(user.getPassword());
		if (userDB == null) {
			if (validEmail && validPassword) {
				User logUser = new User();
				String token = getJWTToken(user.getEmail());
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				logUser.setEmail(user.getEmail());
				logUser.setToken(token);
				logUser.setPhones(user.getPhones());
				iUserservice.save(user);
				return ResponseEntity.ok().body(logUser);
			} else {
				throw new ResourceBadRequestException("El email o passoword ingresado no es válido.");
			}
		} else {
			throw new ResourceBadRequestException("Usuario con el email '" + user.getEmail() + "' ya esta registrado.");
		}
	}
	
	// Login
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) throws ResourceBadRequestException {
		User userDB = iUserservice.findByEmail(user);

		if (userDB == null) {
			throw new ResourceBadRequestException("El email o passoword ingresado no es válido.");
		}
		// Test passwords
		boolean result = bCryptPasswordEncoder.matches(user.getPassword(), userDB.getPassword());

		if (result == false) {
			throw new ResourceBadRequestException("El email o passoword ingresado no es válido.");
		} else {
			String token = getJWTToken(user.getEmail());
			User logUser = new User();
			logUser.setEmail(user.getEmail());
			logUser.setToken(token);
			logUser.setPassword(":)");
			userDB.setLast_login(new Date());
			iUserservice.updateUser(userDB);
			return ResponseEntity.ok().body(logUser);
		}
	}

	@PostMapping("/rev-token")
	public ResponseEntity<User> revalidateToken(@RequestBody User user) {

		String token = getJWTToken(user.getEmail());
		User logUser = new User();
		logUser.setEmail(user.getEmail());
		logUser.setToken(token);
//		Map<String, String> response = new HashMap<>();
//		response.put("token", token);
//
//		return response;
		return ResponseEntity.ok().body(logUser);
	}

	// JWT
	private String getJWTToken(String email) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
