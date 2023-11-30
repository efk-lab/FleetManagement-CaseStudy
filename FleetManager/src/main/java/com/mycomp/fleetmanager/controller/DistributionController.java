package com.mycomp.fleetmanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetDistributionRequest;
import com.mycomp.fleetmanager.model.GetDistributionResponse;
import com.mycomp.fleetmanager.model.SaveDistributionRequest;
import com.mycomp.fleetmanager.model.SaveDistributionResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RequestMapping("/fleetmanager/distribution")
public interface DistributionController {

	@RequestMapping(
			value = "/record", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Save distribution", notes = "Returns HTTP 404 or 500 if the distribution not saved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Distribution saved"),
			@ApiResponse(code = 404, message = "Distribution not saved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<SaveDistributionResponse> saveDistribution(@Valid @RequestBody SaveDistributionRequest saveDistributionRequest) throws FleetManagerException;
	
	@RequestMapping(
			value = "/details", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get distribution", notes = "Returns HTTP 404 or 500 if the distribution not retrieved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Distribution retrieved"),
			@ApiResponse(code = 404, message = "Distribution not retrieved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN', 'FLEET_MNGR_SYSTEM')")
	public ResponseEntity<GetDistributionResponse> getDistribution(@Valid @RequestBody GetDistributionRequest getDistributionRequest) throws FleetManagerException;
	
}