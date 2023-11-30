package com.mycomp.fleetmanager.controller.impl;

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
import com.mycomp.fleetmanager.FleetManagerApplicationTest;
import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.model.GetDistributionRequest;
import com.mycomp.fleetmanager.model.GetDistributionResponse;
import com.mycomp.fleetmanager.model.SaveDistributionRequest;
import com.mycomp.fleetmanager.model.SaveDistributionResponse;
import com.mycomp.fleetmanager.service.DistributionService;

@ContextConfiguration(classes = FleetManagerApplicationTest.class)
@WebMvcTest(DistributionControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(DistributionControllerImpl.class)
public class DistributionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DistributionService distributionService;
	
	@MockBean
	private UserService userService;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;
	

	@Test
	public void testSaveDistribution() throws Exception {

		SaveDistributionRequest saveDistributionRequest = new SaveDistributionRequest();
		SaveDistributionResponse saveDistributionResponse = prepareSaveDistributionResponse();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(distributionService.saveDistribution(any(SaveDistributionRequest.class))).willReturn(saveDistributionResponse);

		this.mockMvc.perform(post("/fleetmanager/distribution/record")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveDistributionRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("plate").value("34 TL 34"))
		.andExpect(jsonPath("distributionId").value("62d322ddf9f5e01864bed242"));

	}
	
	@Test
	public void testSaveDistributionReturnsNoContent() throws Exception {

		SaveDistributionRequest saveDistributionRequest = new SaveDistributionRequest();
		SaveDistributionResponse saveDistributionResponse = null;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(distributionService.saveDistribution(any(SaveDistributionRequest.class))).willReturn(saveDistributionResponse);

		this.mockMvc.perform(post("/fleetmanager/distribution/record")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveDistributionRequest)))
		.andExpect(status().isNoContent());

	}
	
	@Test
	public void testGetDistribution() throws Exception {

		GetDistributionRequest getDistributionRequest = new GetDistributionRequest();
		GetDistributionResponse getDistributionResponse = prepareGetDistributionResponse();
		
		given(userService.getUser()).willReturn("eda@gmail.com");
		given(distributionService.getDistribution(any(GetDistributionRequest.class))).willReturn(getDistributionResponse);

		this.mockMvc.perform(post("/fleetmanager/distribution/details")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getDistributionRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("plate").value("34 TL 34"));

	}
	
	@Test
	public void testGetDistributionReturnsNoContent() throws Exception {

		GetDistributionRequest getDistributionRequest = new GetDistributionRequest();
		GetDistributionResponse getDistributionResponse = null;
		
		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(distributionService.getDistribution(any(GetDistributionRequest.class))).willReturn(getDistributionResponse);

		this.mockMvc.perform(post("/fleetmanager/distribution/details")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getDistributionRequest)))
		.andExpect(status().isNoContent());

	}
	
	
	private SaveDistributionResponse prepareSaveDistributionResponse() {
		
		SaveDistributionResponse saveDistributionResponse = new SaveDistributionResponse();
		
		saveDistributionResponse.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		saveDistributionResponse.setPlate("34 TL 34");

		return saveDistributionResponse;
		
	}
	
	private GetDistributionResponse prepareGetDistributionResponse() {
		
		GetDistributionResponse setDistributionResponse = new GetDistributionResponse();
		
		setDistributionResponse.setPlate("34 TL 34");
		setDistributionResponse.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		
		return setDistributionResponse;
		
	}
}
