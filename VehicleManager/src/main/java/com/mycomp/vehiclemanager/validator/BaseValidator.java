package com.mycomp.vehiclemanager.validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.vehiclemanager.conf.security.server.UserService;
import com.mycomp.vehiclemanager.error.VehicleManagerException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class BaseValidator {
	
	@Autowired
	protected UserService userService;


	protected void validateRequest(Object request) {

		if (request == null) {
			log.error("Error during validation of request. Details: Request cannot be null.");
			throw new VehicleManagerException("Request cannot be null.");
		}

	}

}