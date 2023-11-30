package com.mycomp.customermanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.customermanager.error.CustomerManagerException;
import com.mycomp.customermanager.model.GetShipmentStateRequest;
import com.mycomp.customermanager.model.GetShipmentStateResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/customermanager/shipment")
public interface ShipmentStateController {

	@RequestMapping(
			value = "/state", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get shipment state", notes = "Returns HTTP 404 or 500 if the shipment state not retrieved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "ShipmentState retrived"),
			@ApiResponse(code = 404, message = "ShipmentState not retrived"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('CUSTOMER_MNGR_USER', 'CUSTOMER_MNGR_ADMIN')")
	public ResponseEntity<GetShipmentStateResponse> getShipmentState(@Valid @RequestBody GetShipmentStateRequest getShipmentStateRequest) throws CustomerManagerException;
	
}