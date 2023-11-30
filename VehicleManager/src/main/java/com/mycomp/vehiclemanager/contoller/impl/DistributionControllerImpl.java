package com.mycomp.vehiclemanager.contoller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.vehiclemanager.controller.DistributionController;
import com.mycomp.vehiclemanager.error.VehicleManagerException;
import com.mycomp.vehiclemanager.model.StartDistributionRequest;
import com.mycomp.vehiclemanager.model.StartDistributionResponse;
import com.mycomp.vehiclemanager.service.DistributionService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "DistributionService")
@Slf4j
public class DistributionControllerImpl extends BaseControllerImpl implements DistributionController {

	@Autowired
	private DistributionService distributionService;
	

	@Override
	public ResponseEntity<StartDistributionResponse> startDistribution(StartDistributionRequest startDistributionRequest) throws VehicleManagerException {

		log.info("StartDistributionRequest received. StartDistributionRequest: " + startDistributionRequest.toString() + " RequestedBy:" + userService.getUser());
		
		StartDistributionResponse startDistributionResponse = distributionService.startDistribution(startDistributionRequest);

		if (startDistributionResponse != null) {
			return new ResponseEntity<>(startDistributionResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(startDistributionResponse, HttpStatus.NO_CONTENT);
		}

	}

}
