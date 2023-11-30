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
import com.mycomp.fleetmanager.model.SaveVehicleRequest;
import com.mycomp.fleetmanager.model.SaveVehicleResponse;
import com.mycomp.fleetmanager.service.VehicleService;

@ContextConfiguration(classes = FleetManagerApplicationTest.class)
@WebMvcTest(VehicleControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(VehicleControllerImpl.class)
public class VehicleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private VehicleService vehicleService;

	@MockBean
	private UserService userService;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;

	@Test
	public void testSaveVehicle() throws Exception {

		SaveVehicleRequest saveVehicleRequest = new SaveVehicleRequest();
		SaveVehicleResponse saveVehicleResponse = prepareSaveVehicleResponse() ;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(vehicleService.saveVehicle(any(SaveVehicleRequest.class))).willReturn(saveVehicleResponse);

		this.mockMvc.perform(post("/fleetmanager/vehicle/record").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveVehicleRequest)))
				.andExpect(status().isOk()).andExpect(jsonPath("plate").value("34 TL 34"));

	}
	
	@Test
	public void testSaveVehicleReturnsNoContent() throws Exception {

		SaveVehicleRequest saveVehicleRequest = new SaveVehicleRequest();
		SaveVehicleResponse saveVehicleResponse = null;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(vehicleService.saveVehicle(any(SaveVehicleRequest.class))).willReturn(saveVehicleResponse);

		this.mockMvc.perform(post("/fleetmanager/vehicle/record").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveVehicleRequest)))
				.andExpect(status().isNoContent());
	}
	
	
	private SaveVehicleResponse prepareSaveVehicleResponse() {
		
		SaveVehicleResponse saveVehicleResponse = new SaveVehicleResponse();
		
		saveVehicleResponse.setPlate("34 TL 34");
		saveVehicleResponse.setVehicleId(new ObjectId("62d322ddf9f5e01864bed242"));
		
		return saveVehicleResponse;
	}
}
