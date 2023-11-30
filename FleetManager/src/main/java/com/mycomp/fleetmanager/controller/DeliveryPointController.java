package com.mycomp.fleetmanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.SaveDeliveryPointRequest;
import com.mycomp.fleetmanager.model.SaveDeliveryPointResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RequestMapping("/fleetmanager/deliverypoint")
public interface DeliveryPointController {

	@RequestMapping(
			value = "/record", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Save DeliveryPoint", notes = "Returns HTTP 404 or 500 if the delivery point not saved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "DeliveryPoint saved"),
			@ApiResponse(code = 404, message = "DeliveryPoint not saved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<SaveDeliveryPointResponse> saveDeliveryPoint(@Valid @RequestBody SaveDeliveryPointRequest saveDeliveryPointRequest) throws FleetManagerException;

}

