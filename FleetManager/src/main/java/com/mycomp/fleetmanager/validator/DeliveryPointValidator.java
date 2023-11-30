package com.mycomp.fleetmanager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.SaveDeliveryPointRequest;
import com.mycomp.fleetmanager.repository.DeliveryPointRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeliveryPointValidator extends BaseValidator {

	@Autowired
	private DeliveryPointRepository deliveryPointRepository;


	public void validateSaveDeliveryPointRequest(SaveDeliveryPointRequest saveDeliveryPointRequest) {

		validateRequest(saveDeliveryPointRequest);
		validateDeliveryPointName(saveDeliveryPointRequest.getDeliveryPointName());
		validateDeliveryPointValue(saveDeliveryPointRequest.getDeliveryPointValue());

		log.info("SaveDeliveryPointRequest validated. SaveDeliveryPointRequest: " + saveDeliveryPointRequest.toString() + " User:" + userService.getUser());

	}

	private void validateDeliveryPointName(String deliveryPointName) {

		DeliveryPoint deliveryPoint = deliveryPointRepository.findByDeliveryPointName(deliveryPointName);
		
		if (deliveryPoint != null) {
			throw new FleetManagerException("A DeliveryPoint with spesified name already exists.");
		}

	}

	private void validateDeliveryPointValue(int deliveryPointValue) {

		DeliveryPoint deliveryPoint = deliveryPointRepository.findByDeliveryPointValue(deliveryPointValue);
		
		if (deliveryPoint != null) {
			throw new FleetManagerException("A DeliveryPoint with spesified value already exists.");
		}

	}

}