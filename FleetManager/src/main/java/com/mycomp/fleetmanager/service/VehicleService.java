package com.mycomp.fleetmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.fleetmanager.document.Vehicle;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.mapper.VehicleMapper;
import com.mycomp.fleetmanager.model.GetVehicleRequest;
import com.mycomp.fleetmanager.model.GetVehicleResponse;
import com.mycomp.fleetmanager.model.SaveVehicleRequest;
import com.mycomp.fleetmanager.model.SaveVehicleResponse;
import com.mycomp.fleetmanager.repository.VehicleRepository;
import com.mycomp.fleetmanager.validator.VehicleValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleService extends BaseService {

	@Autowired
	private VehicleValidator vehicleValidator;

	@Autowired
	private VehicleMapper vehicleMapper;

	@Autowired
	private VehicleRepository vehicleRepository;
	

	public SaveVehicleResponse saveVehicle(SaveVehicleRequest saveVehicleRequest) throws FleetManagerException {

		Vehicle vehicle = null;
		SaveVehicleResponse saveVehicleResponse = null;
		Vehicle savedVehicle = null;

		vehicleValidator.validateSaveVehicleRequest(saveVehicleRequest);
		vehicle = vehicleMapper.toVehicle(saveVehicleRequest);
		savedVehicle = vehicleRepository.save(vehicle);
		saveVehicleResponse = vehicleMapper.toSaveVehicleResponse(savedVehicle);
		
		log.info("Vehicle saved. SaveVehicleResponse: " + saveVehicleResponse.toString() + " User:" + userService.getUser());

		return saveVehicleResponse;

	}
	
	
	public GetVehicleResponse getVehicle(GetVehicleRequest getVehicleRequest) throws FleetManagerException {
		
		GetVehicleResponse getVehicleResponse = null;
		
		Vehicle vehicle = vehicleRepository.findByPlate(getVehicleRequest.getPlate());
		getVehicleResponse = vehicleMapper.toGetVehicleResponse(vehicle);
		
		log.info("Vehicle retrieved. GetVehicleResponse: " + getVehicleResponse.toString() + " User:" + userService.getUser());

		return getVehicleResponse;

	}

}