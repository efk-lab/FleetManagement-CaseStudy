package com.mycomp.customermanager.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.customermanager.conf.security.UserService;
import com.mycomp.customermanager.error.CustomerManagerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class BaseValidator {

	@Autowired
	protected UserService userService;


	protected void validateRequest(Object request) {

		if (request == null) {
			log.error("Error during validation of request. Details: Request cannot be null.");
			throw new CustomerManagerException("Request cannot be null.");
		}

	}

}
