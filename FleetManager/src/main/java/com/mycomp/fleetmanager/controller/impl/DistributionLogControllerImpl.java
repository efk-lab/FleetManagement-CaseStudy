package com.mycomp.fleetmanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.fleetmanager.controller.DistributionLogController;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetDistributionLogRequest;
import com.mycomp.fleetmanager.model.GetDistributionLogResponse;
import com.mycomp.fleetmanager.service.DistributionLogService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "DistributionLogService")
@Slf4j
public class DistributionLogControllerImpl extends BaseControllerImpl implements DistributionLogController {
	
	@Autowired
	private DistributionLogService distributionLogService;


	@Override
	public ResponseEntity<GetDistributionLogResponse> getDistributionLog(GetDistributionLogRequest getDistributionLogRequest) throws FleetManagerException {

		log.info("GetDistributionLogRequest received: " + getDistributionLogRequest.toString() + " RequestedBy: " + userService.getUser());
		
		GetDistributionLogResponse getDistributionLogResult = distributionLogService.getDistributionLog(getDistributionLogRequest);
	
		if (getDistributionLogResult != null) {
			return new ResponseEntity<>(getDistributionLogResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(getDistributionLogResult, HttpStatus.NO_CONTENT);
		}

	}
}
