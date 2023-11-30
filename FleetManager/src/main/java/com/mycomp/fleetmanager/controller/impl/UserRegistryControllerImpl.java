package com.mycomp.fleetmanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.fleetmanager.controller.UserRegistryController;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.SignUpRequest;
import com.mycomp.fleetmanager.model.SignUpResponse;
import com.mycomp.fleetmanager.service.UserRegistryService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "UserRegistryService")
@Slf4j
public class UserRegistryControllerImpl extends BaseControllerImpl implements UserRegistryController {

	@Autowired
	private UserRegistryService userRegistryService;

	
	@Override
	public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) throws FleetManagerException {

		log.info("SignUpRequest received: " + signUpRequest.toString() + " RequestedBy: " + userService.getUser());

		SignUpResponse signUpResponse = userRegistryService.signUp(signUpRequest);

		if (signUpResponse != null) {
			return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(signUpResponse, HttpStatus.NO_CONTENT);
		}

	}

}
