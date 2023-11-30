package com.mycomp.fleetmanager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.document.Vehicle;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.SaveVehicleRequest;
import com.mycomp.fleetmanager.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VehicleValidator extends BaseValidator {

	@Autowired
	private VehicleRepository vehicleRepository;
	

	public void validateSaveVehicleRequest(SaveVehicleRequest saveVehicleRequest) throws FleetManagerException {

		validateRequest(saveVehicleRequest);
		validatePlate(saveVehicleRequest.getPlate());

		log.info("SaveVehicleRequest validated. SaveVehicleRequest : " + saveVehicleRequest.toString() + " User:" + userService.getUser());

	}

	private void validatePlate(String plate) {

		Vehicle vehicle = vehicleRepository.findByPlate(plate);
		if (vehicle != null) {
			throw new FleetManagerException("Vehicle already exists.");
		}

	}

}
