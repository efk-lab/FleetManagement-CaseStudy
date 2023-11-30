package com.mycomp.vehiclemanager.contoller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.vehiclemanager.VehicleManagerApplicationTest;
import com.mycomp.vehiclemanager.conf.security.server.UserService;
import com.mycomp.vehiclemanager.model.SignUpRequest;
import com.mycomp.vehiclemanager.model.SignUpResponse;
import com.mycomp.vehiclemanager.service.UserRegistryService;

@ContextConfiguration(classes = VehicleManagerApplicationTest.class)
@WebMvcTest(UserRegistryControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UserRegistryControllerImpl.class)
public class UserRegistryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRegistryService userRegistryService;
	
	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;


	@Test
	public void testSignUp() throws Exception {

		SignUpRequest signUpRequest = new SignUpRequest();
		SignUpResponse signUpResponse = prepareSignUpResponse();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(userRegistryService.signUp(any(SignUpRequest.class))).willReturn(signUpResponse);

		this.mockMvc.perform(post("/vehiclemanager/registry/sign-up")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(signUpRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("email").value("xxx@gmail.com"));
		
	}
	
	@Test
	public void testSignUpReturnsNoContent() throws Exception {

		SignUpRequest signUpRequest = new SignUpRequest();
		SignUpResponse signUpResponse = null;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(userRegistryService.signUp(any(SignUpRequest.class))).willReturn(signUpResponse);
		this.mockMvc.perform(post("/vehiclemanager/registry/sign-up")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(signUpRequest)))
		.andExpect(status().isNoContent());
		
	}
	
	private SignUpResponse prepareSignUpResponse() {
		
		SignUpResponse signUpResponse = new SignUpResponse();
		
		signUpResponse.setUserId(new ObjectId("62d322ddf9f5e01864bed242"));
		signUpResponse.setEmail("xxx@gmail.com");
		
		return signUpResponse;
	}
}
