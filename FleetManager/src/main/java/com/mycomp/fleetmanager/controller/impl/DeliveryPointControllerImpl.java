package com.mycomp.fleetmanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.fleetmanager.controller.DeliveryPointController;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.SaveDeliveryPointRequest;
import com.mycomp.fleetmanager.model.SaveDeliveryPointResponse;
import com.mycomp.fleetmanager.service.DeliveryPointService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "DeliveryPointService")
@Slf4j
public class DeliveryPointControllerImpl extends BaseControllerImpl implements DeliveryPointController {
	
	@Autowired
	private DeliveryPointService deliveryPointService;
	

	@Override
	public ResponseEntity<SaveDeliveryPointResponse> saveDeliveryPoint(SaveDeliveryPointRequest saveDeliveryPointRequest) throws FleetManagerException {

		log.info("SaveDeliveryPointRequest received: " + saveDeliveryPointRequest.toString() + " RequestedBy: " + userService.getUser());
		
		SaveDeliveryPointResponse saveDeliveryPointResult = deliveryPointService.saveDeliveryPoint(saveDeliveryPointRequest);

		if (saveDeliveryPointResult != null) {
			return new ResponseEntity<>(saveDeliveryPointResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveDeliveryPointResult, HttpStatus.NO_CONTENT);
		}

	}

}
