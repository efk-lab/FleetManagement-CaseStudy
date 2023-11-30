package com.mycomp.fleetmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.fleetmanager.document.User;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.mapper.UserMapper;
import com.mycomp.fleetmanager.model.SignUpRequest;
import com.mycomp.fleetmanager.model.SignUpResponse;
import com.mycomp.fleetmanager.repository.UserRepository;
import com.mycomp.fleetmanager.validator.UserValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistryService extends BaseService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserValidator userValidator;


	public SignUpResponse signUp(SignUpRequest signUpRequest) throws FleetManagerException {

		SignUpResponse signUpResponse = null;

		userValidator.validateSignUpRequest(signUpRequest);
		User user = userMapper.toUser(signUpRequest);
		User savedUser = userRepository.save(user);
		signUpResponse = userMapper.toSignUpResponse(savedUser);
		
		log.info("User saved. SignUpResponse: " + signUpResponse.toString() + " User:" + userService.getUser());

		return signUpResponse;
	}
	
}
