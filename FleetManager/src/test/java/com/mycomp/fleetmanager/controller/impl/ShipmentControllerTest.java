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
import com.mycomp.fleetmanager.model.AssignPackageToBagRequest;
import com.mycomp.fleetmanager.model.AssignPackageToBagResponse;
import com.mycomp.fleetmanager.model.GetShipmentRequest;
import com.mycomp.fleetmanager.model.GetShipmentResponse;
import com.mycomp.fleetmanager.model.SaveBagRequest;
import com.mycomp.fleetmanager.model.SaveBagResponse;
import com.mycomp.fleetmanager.model.SavePackageRequest;
import com.mycomp.fleetmanager.model.SavePackageResponse;
import com.mycomp.fleetmanager.service.ShipmentService;



@ContextConfiguration(classes = FleetManagerApplicationTest.class)
@WebMvcTest(ShipmentControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(ShipmentControllerImpl.class)
public class ShipmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ShipmentService shipmentService;

	@MockBean
	private UserService userService;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;
	
	
	@Test
	public void testSavePackage() throws Exception {

		SavePackageRequest savePackageRequest = new SavePackageRequest();
		SavePackageResponse savePackageResponse = prepareSavePackageResponse();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.savePackage(any(SavePackageRequest.class))).willReturn(savePackageResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/package").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(savePackageRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("barcode").value("P8988000125"));
			
	}
	
	@Test
	public void testSaveShipmentReturnsNoContent() throws Exception {


		SavePackageRequest savePackageRequest = new SavePackageRequest();
		SavePackageResponse savePackageResponse = null;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.savePackage(any(SavePackageRequest.class))).willReturn(savePackageResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/package").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(savePackageRequest)))
				.andExpect(status().isNoContent());
			
	}
	
	@Test
	public void testSaveBag() throws Exception {

		SaveBagRequest saveBagRequest = new SaveBagRequest();
		SaveBagResponse saveBagResponse = prepareSaveBagResponse();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.saveBag(any(SaveBagRequest.class))).willReturn(saveBagResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/bag").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveBagRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("barcode").value("P8988000125"));
			
	}
	
	@Test
	public void testSaveBagReturnsNoContent() throws Exception {


		SaveBagRequest saveBagRequest = new SaveBagRequest();
		SaveBagResponse saveBagResponse = null;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.saveBag(any(SaveBagRequest.class))).willReturn(saveBagResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/bag").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveBagRequest)))
				.andExpect(status().isNoContent());
			
	}
	
	
	@Test
	public void testGetShipment() throws Exception {

		GetShipmentRequest getShipmentRequest = new GetShipmentRequest();
		GetShipmentResponse getShipmentResponse = prepareGetShipmentResponse();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.getShipment(any(GetShipmentRequest.class))).willReturn(getShipmentResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/details").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getShipmentRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("barcode").value("P8988000125"));

	}
	
	@Test
	public void testGetShipmentReturnsNoContent() throws Exception {

		GetShipmentRequest getShipmentRequest = new GetShipmentRequest();
		GetShipmentResponse getShipmentResponse = null;

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.getShipment(any(GetShipmentRequest.class))).willReturn(getShipmentResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/details").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getShipmentRequest)))
				.andExpect(status().isNoContent());

	}
	
	@Test
	public void testAssignPackageToBag() throws Exception {

		AssignPackageToBagRequest assignPackageToBagRequest = new AssignPackageToBagRequest();
		AssignPackageToBagResponse assignPackageToBagResponse = prepareAssignPackageToBagResponse();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.assignPackageToBag(any(AssignPackageToBagRequest.class))).willReturn(assignPackageToBagResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/package-to-bag").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(assignPackageToBagRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("bagBarcode").value("P8988000126"));

	}
	
	@Test
	public void testAssignPackageToBagReturnsNoContent() throws Exception {

		AssignPackageToBagRequest assignPackageToBagRequest = new AssignPackageToBagRequest();
		AssignPackageToBagResponse assignPackageToBagResponse = null;
		
		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(shipmentService.assignPackageToBag(any(AssignPackageToBagRequest.class))).willReturn(assignPackageToBagResponse);

		this.mockMvc.perform(post("/fleetmanager/shipment/package-to-bag").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(assignPackageToBagRequest)))
				.andExpect(status().isNoContent());

	}
	
	private SavePackageResponse prepareSavePackageResponse() {
		
		SavePackageResponse savePackageResponse = new SavePackageResponse();
		
		savePackageResponse.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		savePackageResponse.setBarcode("P8988000125");

		return savePackageResponse;
		
	}
	
	private SaveBagResponse prepareSaveBagResponse() {
		
		SaveBagResponse saveBagResponse = new SaveBagResponse();
		
		saveBagResponse.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		saveBagResponse.setBarcode("P8988000125");

		return saveBagResponse;
		
	}
	
	private GetShipmentResponse prepareGetShipmentResponse() {
		
		GetShipmentResponse getShipmentResponse = new GetShipmentResponse();
		
		getShipmentResponse.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		getShipmentResponse.setBarcode("P8988000125");

		return getShipmentResponse;
		
	}
	
	private AssignPackageToBagResponse prepareAssignPackageToBagResponse() {
		
		AssignPackageToBagResponse assignPackageToBagResponse = new AssignPackageToBagResponse();

		assignPackageToBagResponse.setPackageBarcode("P8988000125");
		assignPackageToBagResponse.setBagBarcode("P8988000126");
		
		return assignPackageToBagResponse;
	}
}
