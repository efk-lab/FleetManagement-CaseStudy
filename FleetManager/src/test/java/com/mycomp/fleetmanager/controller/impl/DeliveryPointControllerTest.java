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
import com.mycomp.fleetmanager.model.SaveDeliveryPointRequest;
import com.mycomp.fleetmanager.model.SaveDeliveryPointResponse;
import com.mycomp.fleetmanager.service.DeliveryPointService;

@ContextConfiguration(classes = FleetManagerApplicationTest.class)
@WebMvcTest(DeliveryPointControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(DeliveryPointControllerImpl.class)
public class DeliveryPointControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeliveryPointService deliveryPointService;
	
	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;
	

	@Test
	public void testSaveDeliveryPoint() throws Exception {

		SaveDeliveryPointRequest saveDeliveryPointRequest = new SaveDeliveryPointRequest();
		SaveDeliveryPointResponse saveDeliveryPointResponse = prepareSaveDeliveryPointResponse();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(deliveryPointService.saveDeliveryPoint(any(SaveDeliveryPointRequest.class))).willReturn(saveDeliveryPointResponse);

		this.mockMvc.perform(post("/fleetmanager/deliverypoint/record")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveDeliveryPointRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("deliveryPoint").value("Branch"));

	}
	
	@Test
	public void testSaveDeliveryPointReturnsNoContent() throws Exception {

		SaveDeliveryPointRequest saveDeliveryPointRequest = new SaveDeliveryPointRequest();
		SaveDeliveryPointResponse saveDeliveryPointResponse = null;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(deliveryPointService.saveDeliveryPoint(any(SaveDeliveryPointRequest.class))).willReturn(saveDeliveryPointResponse);

		this.mockMvc.perform(post("/fleetmanager/deliverypoint/record")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveDeliveryPointRequest)))
		        .andExpect(status().isNoContent());

	}
	
	private SaveDeliveryPointResponse prepareSaveDeliveryPointResponse() {
		SaveDeliveryPointResponse saveDeliveryPointResponse = new SaveDeliveryPointResponse();
		
		saveDeliveryPointResponse.setDeliveryPointId(new ObjectId("62d322ddf9f5e01864bed242"));
		saveDeliveryPointResponse.setDeliveryPoint("Branch");
		
		return saveDeliveryPointResponse;
	}
}
