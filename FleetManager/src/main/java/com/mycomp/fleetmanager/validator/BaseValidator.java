package com.mycomp.fleetmanager.validator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.repository.DistributionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class BaseValidator {

	@Autowired
	protected UserService userService;

	@Autowired
	private DistributionRepository distributionRepository;
	

	protected void validateRequest(Object request) {

		if (request == null) {
			log.error("Error during validation of request. Details: Request cannot be null.");
			throw new FleetManagerException("Request cannot be null.");
		}

	}

	protected void validateDistributionId(ObjectId distributionId) {

		if (distributionId == null) {
			throw new FleetManagerException("DistributionId cannot be empty.");
		}

		if (distributionRepository.findById(distributionId).isEmpty()) {
			throw new FleetManagerException("Distribution does not exist.");
		}

	}

}
