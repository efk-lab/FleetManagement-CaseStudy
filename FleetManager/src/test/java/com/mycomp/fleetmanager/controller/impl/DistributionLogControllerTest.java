package com.mycomp.fleetmanager.controller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.mycomp.fleetmanager.FleetManagerApplicationTest;
import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.model.GetDistributionLogRequest;
import com.mycomp.fleetmanager.model.GetDistributionLogResponse;
import com.mycomp.fleetmanager.service.DistributionLogService;

@ContextConfiguration(classes = FleetManagerApplicationTest.class)
@WebMvcTest(DistributionLogControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(DistributionLogControllerImpl.class)
public class DistributionLogControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DistributionLogService distributionLogService;
	
	@MockBean
	private UserService userService;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;
	
	@Test
	public void testGetDistributionLog() throws Exception {

		GetDistributionLogRequest getDistributionLogRequest = new GetDistributionLogRequest();
		GetDistributionLogResponse getDistributionLogResponse = prepareGetDistributionLogResponse();
		
		given(userService.getUser()).willReturn("eda@gmail.com");
		given(distributionLogService.getDistributionLog(any(GetDistributionLogRequest.class))).willReturn(getDistributionLogResponse);

		this.mockMvc.perform(post("/fleetmanager/distributionlog/details")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getDistributionLogRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("plate").value("34 TL 34"));

	}
	
	@Test
	public void testGetDistributionLogReturnsNoContent() throws Exception {

		GetDistributionLogRequest getDistributionLogRequest = new GetDistributionLogRequest();
		GetDistributionLogResponse getDistributionLogResponse = null;
		
		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(distributionLogService.getDistributionLog(any(GetDistributionLogRequest.class))).willReturn(getDistributionLogResponse);

		this.mockMvc.perform(post("/fleetmanager/distributionlog/details")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getDistributionLogRequest)))
		        .andExpect(status().isNoContent());

	}
	
	private GetDistributionLogResponse prepareGetDistributionLogResponse() {
		
		GetDistributionLogResponse getDistributionLogResponse = new GetDistributionLogResponse();
		
		getDistributionLogResponse.setPlate("34 TL 34");
		
		return getDistributionLogResponse;
	}
}
