package com.mycomp.fleetmanager.validator;

import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetDistributionLogRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DistributionLogValidator extends BaseValidator {

	public void validateGetDistributionLogRequest(GetDistributionLogRequest getDistributionLogRequest) throws FleetManagerException {

		validateRequest(getDistributionLogRequest);
		validateDistributionId(getDistributionLogRequest.getDistributionId());

		log.info("GetDistributionLogRequest validated. GetDistributionLogRequest: " + getDistributionLogRequest.toString() + " User:" + userService.getUser());
		
	}

}
