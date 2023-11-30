package com.mycomp.customermanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.customermanager.controller.ShipmentStateController;
import com.mycomp.customermanager.error.CustomerManagerException;
import com.mycomp.customermanager.model.GetShipmentStateRequest;
import com.mycomp.customermanager.model.GetShipmentStateResponse;
import com.mycomp.customermanager.service.ShipmentStateService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


@RestController
@Api(value = "ShipmentStateService")
@Slf4j
public class ShipmentStateControllerImpl extends BaseControllerImpl implements ShipmentStateController {

	@Autowired
	private ShipmentStateService shipmentStateService;
	
	
	@Override
	public ResponseEntity<GetShipmentStateResponse> getShipmentState(GetShipmentStateRequest getShipmentStateRequest) throws CustomerManagerException {
		
		log.info("GetShipmentStateRequest received: " + getShipmentStateRequest.toString() + " RequestedBy: " + userService.getUser());

		GetShipmentStateResponse getShipmentStateResponse = shipmentStateService.getShipmentState(getShipmentStateRequest);
		
		if (getShipmentStateResponse != null) {
			return new ResponseEntity<>(getShipmentStateResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(getShipmentStateResponse, HttpStatus.NO_CONTENT);
		}
		
	}

}
