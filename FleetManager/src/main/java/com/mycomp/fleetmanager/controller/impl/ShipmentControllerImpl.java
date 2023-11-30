package com.mycomp.fleetmanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.fleetmanager.controller.ShipmentController;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.AssignPackageToBagRequest;
import com.mycomp.fleetmanager.model.AssignPackageToBagResponse;
import com.mycomp.fleetmanager.model.GetShipmentRequest;
import com.mycomp.fleetmanager.model.GetShipmentResponse;
import com.mycomp.fleetmanager.model.SaveBagRequest;
import com.mycomp.fleetmanager.model.SaveBagResponse;
import com.mycomp.fleetmanager.model.SavePackageRequest;
import com.mycomp.fleetmanager.model.SavePackageResponse;
import com.mycomp.fleetmanager.service.ShipmentService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "ShipmentService")
@Slf4j
public class ShipmentControllerImpl extends BaseControllerImpl implements ShipmentController {

	@Autowired
	private ShipmentService shipmentService;


	@Override
	public ResponseEntity<SavePackageResponse> savePackage(SavePackageRequest savePackageRequest) throws FleetManagerException {
	
		log.info("SavePackageRequest received: " + savePackageRequest.toString() + " RequestedBy: " + userService.getUser());

		SavePackageResponse savePackageResponse = shipmentService.savePackage(savePackageRequest);

		if (savePackageResponse != null) {
			return new ResponseEntity<>(savePackageResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(savePackageResponse, HttpStatus.NO_CONTENT);
		}

	}

	@Override
	public ResponseEntity<GetShipmentResponse> getShipment(GetShipmentRequest getShipmentRequest) throws FleetManagerException {
		
		log.info("GetShipmentRequest received: " + getShipmentRequest.toString() + " RequestedBy: " + userService.getUser());

		GetShipmentResponse getShipmentResponse = shipmentService.getShipment(getShipmentRequest);

		if (getShipmentResponse != null) {
			return new ResponseEntity<>(getShipmentResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(getShipmentResponse, HttpStatus.NO_CONTENT);
		}
		
	}
	
	@Override
	public ResponseEntity<SaveBagResponse> saveBag(SaveBagRequest saveBagRequest) throws FleetManagerException {
		
		log.info("SaveBagRequest received: " + saveBagRequest.toString() + " RequestedBy: " + userService.getUser());

		SaveBagResponse saveBagResponse = shipmentService.saveBag(saveBagRequest);

		if (saveBagResponse != null) {
			return new ResponseEntity<>(saveBagResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveBagResponse, HttpStatus.NO_CONTENT);
		}
		
	}
	
	@Override
	public ResponseEntity<AssignPackageToBagResponse> assignPackageToBag(AssignPackageToBagRequest assignPackageToBagRequest) throws FleetManagerException {
		
		log.info("AssignPackageToBagRequest received: " + assignPackageToBagRequest.toString() + " RequestedBy: " + userService.getUser());

		AssignPackageToBagResponse assignShipmentResult = shipmentService.assignPackageToBag(assignPackageToBagRequest);

		if (assignShipmentResult != null) {
			return new ResponseEntity<>(assignShipmentResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(assignShipmentResult, HttpStatus.NO_CONTENT);
		}

	}
	
}
