package com.mycomp.fleetmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.fleetmanager.document.DistributionLog;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.mapper.DistributionLogMapper;
import com.mycomp.fleetmanager.model.GetDistributionLogRequest;
import com.mycomp.fleetmanager.model.GetDistributionLogResponse;
import com.mycomp.fleetmanager.repository.DistributionLogRepository;
import com.mycomp.fleetmanager.validator.DistributionLogValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DistributionLogService extends BaseService {

	@Autowired
	private DistributionLogValidator distributionLogValidator;

	@Autowired
	private DistributionLogMapper distributionLogMapper;

	@Autowired
	private DistributionLogRepository distributionLogRepository;
	
	
	public GetDistributionLogResponse getDistributionLog(GetDistributionLogRequest getDistributionLogRequest) throws FleetManagerException {

		DistributionLog distributionLog = null;
		GetDistributionLogResponse distributionLogResponse = null;

		distributionLogValidator.validateGetDistributionLogRequest(getDistributionLogRequest);
		distributionLog = distributionLogRepository.findByDistributionId(getDistributionLogRequest.getDistributionId());
		distributionLogResponse = distributionLogMapper.toGetDistributionLogResponse(distributionLog);
		
		log.info("DistributionLog retrieved. GetDistributionLogResponse: " + distributionLogResponse.toString() + " User:" + userService.getUser());
		
		return distributionLogResponse;
		
	}
}
