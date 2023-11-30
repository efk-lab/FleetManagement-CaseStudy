package com.mycomp.fleetmanager.service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.Distribution;
import com.mycomp.fleetmanager.document.DistributionLog;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.mapper.DistributionLogMapper;
import com.mycomp.fleetmanager.mapper.DistributionMapper;
import com.mycomp.fleetmanager.model.GetDistributionRequest;
import com.mycomp.fleetmanager.model.GetDistributionResponse;
import com.mycomp.fleetmanager.model.SaveDistributionRequest;
import com.mycomp.fleetmanager.model.SaveDistributionResponse;
import com.mycomp.fleetmanager.repository.DistributionLogRepository;
import com.mycomp.fleetmanager.repository.DistributionRepository;
import com.mycomp.fleetmanager.validator.DistributionValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DistributionService extends BaseService {

	@Autowired
	private DistributionValidator distributionValidator;

	@Autowired
	private DistributionMapper distributionMapper;
	
	@Autowired
	private DistributionLogMapper distributionLogMapper;

	@Autowired
	private DistributionRepository distributionRepository;

	@Autowired
	private DistributionLogRepository distributionLogRepository;
	
	@Autowired
	private ShipmentDao shipmentDao;


    @Transactional
	public SaveDistributionResponse saveDistribution(SaveDistributionRequest saveDistributionRequest) throws FleetManagerException {

		Distribution distribution = null;
		DistributionLog distributionLog = null;
		Distribution savedDistribution = null;
		SaveDistributionResponse saveDistributionResponse = null;

		distributionValidator.validateSaveDistributionRequest(saveDistributionRequest);
		distribution = distributionMapper.toDistribution(saveDistributionRequest);
		savedDistribution = distributionRepository.save(distribution);
		distribution.getRoute().stream().forEach(route -> shipmentDao.saveAll(route.getShipments()));	
		distributionLog = distributionLogMapper.toDistributionLog(savedDistribution);
		distributionLogRepository.save(distributionLog);
		saveDistributionResponse = distributionMapper.toSaveDistributionResponse(savedDistribution);
		
		log.info("Distribution saved. SaveDistributionResponse: " + saveDistributionResponse.toString() + " User:" + userService.getUser());
		
		return saveDistributionResponse;

	}

	public GetDistributionResponse getDistribution(GetDistributionRequest getDistributionRequest) throws FleetManagerException {

		Optional<Distribution> distribution = null;
		GetDistributionResponse distributionResponse = null;

		distributionValidator.validateGetDistributionRequest(getDistributionRequest);
		distribution = distributionRepository.findById(new ObjectId(getDistributionRequest.getDistributionId()));
		distributionResponse = distributionMapper.toGetDistributionResponse(distribution);
		
		log.info("Distribution retrieved. GetDistributionResponse: " + distributionResponse.toString() + " User:" + userService.getUser());
		
		return distributionResponse;

	}

}
