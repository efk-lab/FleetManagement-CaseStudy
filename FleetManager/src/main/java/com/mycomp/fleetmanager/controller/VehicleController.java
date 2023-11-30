package com.mycomp.fleetmanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetVehicleRequest;
import com.mycomp.fleetmanager.model.GetVehicleResponse;
import com.mycomp.fleetmanager.model.SaveVehicleRequest;
import com.mycomp.fleetmanager.model.SaveVehicleResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/fleetmanager/vehicle")
public interface VehicleController {

	@RequestMapping(
			value = "/record", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Save vehicle", notes = "Returns HTTP 404 or 500 if the vehicle not saved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Vehicle saved"),
			@ApiResponse(code = 404, message = "Vehicle not saved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<SaveVehicleResponse> saveVehicle(@Valid @RequestBody SaveVehicleRequest saveVehicleRequest) throws FleetManagerException;
	
	@RequestMapping(
			value = "/details", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Save vehicle", notes = "Returns HTTP 404 or 500 if the vehicle not saved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Vehicle retrieved"),
			@ApiResponse(code = 404, message = "Vehicle not retrieved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN', 'FLEET_MNGR_SYSTEM')")
	public ResponseEntity<GetVehicleResponse> getVehicle(@Valid @RequestBody GetVehicleRequest getVehicleRequest) throws FleetManagerException;

}
