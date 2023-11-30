package com.mycomp.fleetmanager.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mycomp.fleetmanager.controller.VehicleController;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetVehicleRequest;
import com.mycomp.fleetmanager.model.GetVehicleResponse;
import com.mycomp.fleetmanager.model.SaveVehicleRequest;
import com.mycomp.fleetmanager.model.SaveVehicleResponse;
import com.mycomp.fleetmanager.service.VehicleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "VehicleService")
@Slf4j
public class VehicleControllerImpl extends BaseControllerImpl implements VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	

	@Override
	public ResponseEntity<SaveVehicleResponse> saveVehicle(SaveVehicleRequest saveVehicleRequest) throws FleetManagerException {

		log.info("SaveVehicleRequest received: " + saveVehicleRequest.toString() + " RequestedBy: " + userService.getUser());

		SaveVehicleResponse saveVehicleResult = vehicleService.saveVehicle(saveVehicleRequest);
		
		if (saveVehicleResult != null) {
			return new ResponseEntity<>(saveVehicleResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveVehicleResult, HttpStatus.NO_CONTENT);
		}

	}

	@Override
	public ResponseEntity<GetVehicleResponse> getVehicle(@Valid GetVehicleRequest getVehicleRequest) throws FleetManagerException {
		
		log.info("GetVehicleRequest received: " + getVehicleRequest.toString() + " RequestedBy: " + userService.getUser());

		GetVehicleResponse getVehicleResponse = vehicleService.getVehicle(getVehicleRequest);
		
		if (getVehicleResponse != null) {
			return new ResponseEntity<>(getVehicleResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(getVehicleResponse, HttpStatus.NO_CONTENT);
		}
		
	}

}
