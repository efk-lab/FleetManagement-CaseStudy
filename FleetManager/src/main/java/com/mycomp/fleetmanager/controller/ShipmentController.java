package com.mycomp.fleetmanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.AssignPackageToBagRequest;
import com.mycomp.fleetmanager.model.AssignPackageToBagResponse;
import com.mycomp.fleetmanager.model.GetShipmentRequest;
import com.mycomp.fleetmanager.model.GetShipmentResponse;
import com.mycomp.fleetmanager.model.SaveBagRequest;
import com.mycomp.fleetmanager.model.SaveBagResponse;
import com.mycomp.fleetmanager.model.SavePackageRequest;
import com.mycomp.fleetmanager.model.SavePackageResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RequestMapping("/fleetmanager/shipment")
public interface ShipmentController {

	@RequestMapping(
			value = "/package", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Save shipment", notes = "Returns HTTP 404 or 500 if the shipment not saved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Shipment saved"),
			@ApiResponse(code = 404, message = "Shipment not saved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<SavePackageResponse> savePackage(@Valid @RequestBody SavePackageRequest savePackageRequest) throws FleetManagerException;
	
	@RequestMapping(
			value = "/details", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get shipment", notes = "Returns HTTP 404 or 500 if the shipment not retrieved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Shipment retrived"),
			@ApiResponse(code = 404, message = "Shipment not retrived"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<GetShipmentResponse> getShipment(@Valid @RequestBody GetShipmentRequest getShipmentRequest) throws FleetManagerException;
	
	@RequestMapping(
			value = "/bag", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Save shipment", notes = "Returns HTTP 404 or 500 if the shipment not saved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Shipment saved"),
			@ApiResponse(code = 404, message = "Shipment not saved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<SaveBagResponse> saveBag(@Valid @RequestBody SaveBagRequest saveBagRequest) throws FleetManagerException;
	
	@RequestMapping(
			value = "/package-to-bag", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Assign shipment", notes = "Returns HTTP 404 or 500 if the shipment not assigned.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "Package assigned to bag"),
			@ApiResponse(code = 404, message = "Package not assigned to bag"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<AssignPackageToBagResponse> assignPackageToBag(@Valid @RequestBody AssignPackageToBagRequest assignPackageToBagRequest) throws FleetManagerException;

}