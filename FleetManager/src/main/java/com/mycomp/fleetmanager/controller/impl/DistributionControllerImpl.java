package com.mycomp.fleetmanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.fleetmanager.controller.DistributionController;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetDistributionRequest;
import com.mycomp.fleetmanager.model.GetDistributionResponse;
import com.mycomp.fleetmanager.model.SaveDistributionRequest;
import com.mycomp.fleetmanager.model.SaveDistributionResponse;
import com.mycomp.fleetmanager.service.DistributionService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "DistributionService")
@Slf4j
public class DistributionControllerImpl extends BaseControllerImpl implements DistributionController {

	@Autowired
	private DistributionService distributionService;
	

	@Override
	public ResponseEntity<SaveDistributionResponse> saveDistribution(SaveDistributionRequest saveDistributionRequest) throws FleetManagerException {

		log.info("SaveDistributionRequest received: " + saveDistributionRequest.toString() + " RequestedBy: " + userService.getUser());
		
		SaveDistributionResponse saveDistributionResult = distributionService.saveDistribution(saveDistributionRequest);
	
		if (saveDistributionResult != null) {
			return new ResponseEntity<>(saveDistributionResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveDistributionResult, HttpStatus.NO_CONTENT);
		}

	}

	@Override
	public ResponseEntity<GetDistributionResponse> getDistribution(GetDistributionRequest getDistributionRequest) throws FleetManagerException {
		
		log.info("GetDistributionRequest received: " + getDistributionRequest.toString() + " RequestedBy: " + userService.getUser());
		
		GetDistributionResponse distributionResult = distributionService.getDistribution(getDistributionRequest);
		

		if (distributionResult != null) {
			return new ResponseEntity<>(distributionResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(distributionResult, HttpStatus.NO_CONTENT);
		}

	}

}
