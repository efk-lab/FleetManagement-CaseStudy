package com.mycomp.fleetmanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.constant.Role;
import com.mycomp.fleetmanager.document.User;
import com.mycomp.fleetmanager.model.SignUpRequest;
import com.mycomp.fleetmanager.model.SignUpResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserMapper extends BaseMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public User toUser(SignUpRequest signUpRequest) {

		log.info("Mapping SignUpRequest to User. SignUpRequest: " + signUpRequest.getEmail() + " User:" + userService.getUser());

		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(Role.FLEET_MNGR_USER);

		return user;

	}

	public SignUpResponse toSignUpResponse(User user) {

		log.info("Mapping User to SignUpResponse. User:" + userService.getUser());

		SignUpResponse signUpResponse = new SignUpResponse();
		
		signUpResponse = (SignUpResponse)toBaseResponse(signUpResponse, user);
		signUpResponse.setUserId(user.getUserId());
		signUpResponse.setEmail(user.getEmail());
		
		return signUpResponse;

	}
}
