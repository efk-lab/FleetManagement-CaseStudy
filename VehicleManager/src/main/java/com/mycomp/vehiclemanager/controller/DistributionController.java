package com.mycomp.vehiclemanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.vehiclemanager.error.VehicleManagerException;
import com.mycomp.vehiclemanager.model.StartDistributionRequest;
import com.mycomp.vehiclemanager.model.StartDistributionResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/vehiclemanager/distribution")
public interface DistributionController {

	@RequestMapping(
			value = "/start-up", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Simulate vehicle route", notes = "Returns HTTP 404 or 500 if the vehicle route not started.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Vehicle route started"),
			@ApiResponse(code = 404, message = "Vehicle route not started"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('VEHICLE_MNGR_USER', 'VEHICLE_MNGR_ADMIN')")
	public ResponseEntity<StartDistributionResponse> startDistribution(@Valid @RequestBody StartDistributionRequest startDistributionRequest) throws VehicleManagerException;
	
}
