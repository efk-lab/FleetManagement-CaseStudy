package com.mycomp.vehiclemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.vehiclemanager.document.User;
import com.mycomp.vehiclemanager.error.VehicleManagerException;
import com.mycomp.vehiclemanager.mapper.UserMapper;
import com.mycomp.vehiclemanager.model.SignUpRequest;
import com.mycomp.vehiclemanager.model.SignUpResponse;
import com.mycomp.vehiclemanager.repository.UserRepository;
import com.mycomp.vehiclemanager.validator.UserValidator;

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

	
	public SignUpResponse signUp(SignUpRequest signUpRequest) throws VehicleManagerException {

		userValidator.validateSignUpRequest(signUpRequest);
		User user = userMapper.toUser(signUpRequest);
		User savedUser = userRepository.save(user);
		SignUpResponse signUpResponse = userMapper.toSignUpResponse(savedUser);
		
		log.info("User saved. SignUpResponse: " + signUpResponse.toString() + " User:" + userService.getUser());

		return signUpResponse;
	}
}

