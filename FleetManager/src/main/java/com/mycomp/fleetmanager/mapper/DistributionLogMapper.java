package com.mycomp.fleetmanager.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.document.DeliveryLocation;
import com.mycomp.fleetmanager.document.DeliveryLocationLog;
import com.mycomp.fleetmanager.document.Distribution;
import com.mycomp.fleetmanager.document.DistributionLog;
import com.mycomp.fleetmanager.dto.DeliveryLocationLogDto;
import com.mycomp.fleetmanager.dto.DeliveryLogDto;
import com.mycomp.fleetmanager.model.GetDistributionLogResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DistributionLogMapper extends BaseMapper {
	
	@Autowired
	private UserService userService;

	public DistributionLog toDistributionLog(Distribution distribution) {

		log.info("Mapping Distribution to DistributionLog. Distribution: " + distribution.toString() + " User:" + userService.getUser());

		DistributionLog distributionLog = new DistributionLog();
		distributionLog.setDistributionId(distribution.getDistributionId());
		distributionLog.setPlate(distribution.getPlate().getPlate());
		distributionLog.setRoute(toDeliveryLocationLog(distribution.getRoute()));
		
		return distributionLog;

	}

	private List<DeliveryLocationLog> toDeliveryLocationLog(List<DeliveryLocation> route) {

		List<DeliveryLocationLog> deliveryLocations = new ArrayList<DeliveryLocationLog>();

		for (DeliveryLocation deliveryLocation : route) {
			DeliveryLocationLog deliveryLocationLog = new DeliveryLocationLog();

			deliveryLocationLog.setDeliveryPoint(deliveryLocation.getDeliveryPoint().getDeliveryPointValue());
			List<String> deliveries = new ArrayList<String>();
			deliveryLocationLog.setDeliveries(deliveries);

			deliveryLocations.add(deliveryLocationLog);
		}
		
		return deliveryLocations;
	}

	public GetDistributionLogResponse toGetDistributionLogResponse(DistributionLog distributionLog) {

		log.info("Mapping DistributionLog to GetDistributionLogResponse." + distributionLog.toString() + " User:" + userService.getUser());

		GetDistributionLogResponse distributionLogResult = new GetDistributionLogResponse();
		distributionLogResult = (GetDistributionLogResponse) toBaseResponse(distributionLogResult, distributionLog);
		distributionLogResult.setPlate(distributionLog.getPlate());
		distributionLogResult.setRoute(toDeliveryLocationDto(distributionLog.getRoute()));
		
		return distributionLogResult;

	}

	private List<DeliveryLocationLogDto> toDeliveryLocationDto(List<DeliveryLocationLog> route) {

		List<DeliveryLocationLogDto> deliveryLocationLogDtoList = new ArrayList<DeliveryLocationLogDto>();

		for (DeliveryLocationLog deliveryLocationLog : route) {
			DeliveryLocationLogDto deliveryLocationLogDto = new DeliveryLocationLogDto();

			deliveryLocationLogDto.setDeliveryPoint(deliveryLocationLog.getDeliveryPoint());
			deliveryLocationLogDto.setDeliveries(toDeliveryLogDto(deliveryLocationLog.getDeliveries()));

			deliveryLocationLogDtoList.add(deliveryLocationLogDto);
		}

		return deliveryLocationLogDtoList;

	}

	private List<DeliveryLogDto> toDeliveryLogDto(List<String> deliveries) {

		List<DeliveryLogDto> deliveryLogDtoList = new ArrayList<DeliveryLogDto>();

		for (String delivery : deliveries) {
			DeliveryLogDto deliveryLogDto = new DeliveryLogDto();

			deliveryLogDto.setBarcode(delivery);

			deliveryLogDtoList.add(deliveryLogDto);
		}
		
		return deliveryLogDtoList;

	}
}
