package com.mycomp.customermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.customermanager.document.User;
import com.mycomp.customermanager.error.CustomerManagerException;
import com.mycomp.customermanager.mapper.UserMapper;
import com.mycomp.customermanager.model.SignUpRequest;
import com.mycomp.customermanager.model.SignUpResponse;
import com.mycomp.customermanager.repository.UserRepository;
import com.mycomp.customermanager.validator.UserValidator;

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


	public SignUpResponse signUp(SignUpRequest signUpRequest) throws CustomerManagerException {

		userValidator.validateSignUpRequest(signUpRequest);
		User user = userMapper.toUser(signUpRequest);
		User savedUser = userRepository.save(user);
		SignUpResponse signUpResponse = userMapper.toSignUpResponse(savedUser);
		
		log.info("User saved. SignUpResponse: " + signUpResponse.toString() + " User:" + userService.getUser());

		return signUpResponse;
		
	}
	
}
