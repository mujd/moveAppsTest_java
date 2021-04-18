package com.mujd.moveAppsTest.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
	private static final String PASSWORD_REGEX = "\\A(?=(.*[0-9]){2,2})(?=.*[a-z])(?=.*[A-Z])\\S{8,}\\z";
	
	private static Pattern pattern;
	private Matcher matcher;

	public PasswordValidator() {
		pattern = Pattern.compile(PASSWORD_REGEX, Pattern.CASE_INSENSITIVE);
	}

	/**
	 * This method validates the input password with PASSWORD_REGEX pattern
	 * 
	 * @param password
	 * @return boolean
	 */
	public boolean isValid(String password) {
		matcher = pattern.matcher(password);
		return matcher.matches();
	}

}