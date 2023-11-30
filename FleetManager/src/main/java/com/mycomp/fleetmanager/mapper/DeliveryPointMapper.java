package com.mycomp.fleetmanager.mapper;

import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.model.SaveDeliveryPointRequest;
import com.mycomp.fleetmanager.model.SaveDeliveryPointResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeliveryPointMapper extends BaseMapper {
	
	public DeliveryPoint toDeliveryPoint(SaveDeliveryPointRequest saveDeliveryPointRequest) {

		log.info("Mapping SaveDeliveryPointRequest to DeliveryPoint. SaveDeliveryPointRequest:" + saveDeliveryPointRequest.toString() + " User:" + userService.getUser());
	
		DeliveryPoint deliveryPoint = new DeliveryPoint();
		deliveryPoint.setDeliveryPointName(saveDeliveryPointRequest.getDeliveryPointName());
		deliveryPoint.setDeliveryPointValue(saveDeliveryPointRequest.getDeliveryPointValue());

		return deliveryPoint;
	}

	public SaveDeliveryPointResponse toSaveDeliveryPointResponse(DeliveryPoint deliveryPoint) {

		log.info("Mapping DeliveryPoint to SaveDeliveryPointResponse. DeliveryPoint:" + deliveryPoint.toString() + " User:" + userService.getUser());
		
		SaveDeliveryPointResponse saveDeliveryPointResponse = new SaveDeliveryPointResponse();
		saveDeliveryPointResponse = (SaveDeliveryPointResponse) toBaseResponse(saveDeliveryPointResponse, deliveryPoint);
		saveDeliveryPointResponse.setDeliveryPointId(deliveryPoint.getDeliveryPointId());
		saveDeliveryPointResponse.setDeliveryPoint(deliveryPoint.getDeliveryPointName());
		
		return saveDeliveryPointResponse;
	}

}