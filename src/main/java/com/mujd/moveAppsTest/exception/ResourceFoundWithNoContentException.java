package com.mujd.moveAppsTest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class ResourceFoundWithNoContentException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceFoundWithNoContentException(String message) {
		super(message);
	}
}
