package com.mycomp.vehiclemanager.mapper;

import org.springframework.stereotype.Component;

import com.mycomp.vehiclemanager.constant.DistributionStatus;
import com.mycomp.vehiclemanager.model.StartDistributionRequest;
import com.mycomp.vehiclemanager.model.StartDistributionResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DistributionMapper extends BaseMapper {
	
	public StartDistributionResponse toStartDistributionResponse(StartDistributionRequest startDistributionRequest) {
		
		log.info("Mapping StartDistributionRequest to StartDistributionResponse. StartDistributionRequest: " + startDistributionRequest.toString()+ " User:" + userService.getUser());
		
		StartDistributionResponse response = new StartDistributionResponse();
		
		response.setPlate(startDistributionRequest.getPlate());
		response.setDistributionId(startDistributionRequest.getDistributionId());
		response.setDistributionStatus(DistributionStatus.DISTRIBUTION_STARTED);
		
		return response;
	
	}

}
