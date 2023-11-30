package com.mycomp.fleetmanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetDistributionLogRequest;
import com.mycomp.fleetmanager.model.GetDistributionLogResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/fleetmanager/distributionlog")
public interface DistributionLogController {
	
	@RequestMapping(
			value = "/details", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Get incorrectly paired deliveryPoint and shipment", notes = "Returns HTTP 404 or 500 if the incorrect pairs not retrieved.")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "DistributionLog retrieved"),
			@ApiResponse(code = 404, message = "DistributionLog not retrieved"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PreAuthorize("hasAnyAuthority('FLEET_MNGR_USER', 'FLEET_MNGR_ADMIN')")
	public ResponseEntity<GetDistributionLogResponse> getDistributionLog(@Valid @RequestBody GetDistributionLogRequest getDistributionLogRequest) throws FleetManagerException;
	
}
