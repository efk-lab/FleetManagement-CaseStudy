package com.mycomp.vehiclemanager.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.vehiclemanager.event.VehicleLocationEvent;
import com.mycomp.vehiclemanager.mapper.DistributionMapper;
import com.mycomp.vehiclemanager.model.StartDistributionRequest;
import com.mycomp.vehiclemanager.model.StartDistributionResponse;
import com.mycomp.vehiclemanager.validator.DistributionValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DistributionService extends BaseService {

	@Autowired
	private VehicleLocationProducer vehicleLocationProducer;
	
	@Autowired
	private DistributionMapper distributionMapper;
	
	@Autowired
	private DistributionValidator distributionValidator;

	
	public StartDistributionResponse startDistribution(StartDistributionRequest startDistributionRequest) {
		
		StartDistributionResponse startDistributionResponse = null; 
	
		distributionValidator.validateStartDistributionRequest(startDistributionRequest);	
		LinkedList<VehicleLocationEvent> vehicleRoute = initializeVehicleRoute(startDistributionRequest.getPlate(), startDistributionRequest.getDistributionId());
		vehicleRoute.stream().forEach(vehicleLocationEvent -> vehicleLocationProducer.produceVehicleLocation(startDistributionRequest.getPlate(), vehicleLocationEvent));
		startDistributionResponse = distributionMapper.toStartDistributionResponse(startDistributionRequest);
		
		log.info("Distribution started. StartDistributionResponse: " + startDistributionResponse.toString() + " User:" + userService.getUser());
		
		return startDistributionResponse;
	
	}

	private LinkedList<VehicleLocationEvent> initializeVehicleRoute(String plate, String distributionId) {
		
	    LinkedList<VehicleLocationEvent> vehicleRoute = new LinkedList<>(List.of(
	    		new VehicleLocationEvent(plate, distributionId, 0, 41.000424, 28.858858),
	    		new VehicleLocationEvent(plate, distributionId, 0, 41.006080, 28.852574),
	    		new VehicleLocationEvent(plate, distributionId, 1, 40.999033, 28.874854),
	    		new VehicleLocationEvent(plate, distributionId, 0, 41.050428, 28.980183),
	    		new VehicleLocationEvent(plate, distributionId, 0, 41.060511, 29.006256),
	    		new VehicleLocationEvent(plate, distributionId, 2, 41.078018, 29.023810),
	    		new VehicleLocationEvent(plate, distributionId, 0, 41.099537, 29.034841),
	    		new VehicleLocationEvent(plate, distributionId, 0, 41.124599, 29.054973),
	    		new VehicleLocationEvent(plate, distributionId, 3, 41.155623, 29.034264)
	    ));
	  
		return vehicleRoute;
		
	}

}
