package com.mycomp.customermanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycomp.customermanager.conf.security.UserService;
import com.mycomp.customermanager.document.User;
import com.mycomp.customermanager.mapper.UserMapper;
import com.mycomp.customermanager.model.SignUpRequest;
import com.mycomp.customermanager.model.SignUpResponse;
import com.mycomp.customermanager.repository.UserRepository;
import com.mycomp.customermanager.validator.UserValidator;


@ExtendWith(MockitoExtension.class)
public class UserRegistryServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserService userService;

	@Mock
	private UserMapper userMapper;

	@Mock
	private UserValidator userValidator;

	@InjectMocks
	private UserRegistryService userRegistryService;
	

	@Test
	public void testSignUp() {

		SignUpRequest signUpRequest = prepareSignUpRequest();
		SignUpResponse signUpResponseExpected = prepareSignUpResponse();
		User user = prepareUser();

		doNothing().when(userValidator).validateSignUpRequest(signUpRequest);
		given(userMapper.toUser(signUpRequest)).willReturn(user);
		given(userRepository.save(user)).willReturn(user);
		given(userMapper.toSignUpResponse(user)).willReturn(signUpResponseExpected);

		SignUpResponse signUpResponseActual = userRegistryService.signUp(signUpRequest);

		assertThat(signUpResponseActual.getEmail()).isEqualTo(signUpResponseExpected.getEmail());

	}

	private SignUpRequest prepareSignUpRequest() {

		SignUpRequest signUpRequest = new SignUpRequest();

		signUpRequest.setEmail("xxx@gmail.com");
		signUpRequest.setPassword("eda123");

		return signUpRequest;

	}

	private SignUpResponse prepareSignUpResponse() {
		SignUpResponse signUpResponse = new SignUpResponse();

		signUpResponse.setUserId(new ObjectId("62d322ddf9f5e01864bed242"));
		signUpResponse.setEmail("xxx@gmail.com");

		return signUpResponse;
	}

	private User prepareUser() {
		User user = new User();

		user.setUserId(new ObjectId("62d322ddf9f5e01864bed242"));
		user.setEmail("eda@gmail.com");

		return user;
	}

}