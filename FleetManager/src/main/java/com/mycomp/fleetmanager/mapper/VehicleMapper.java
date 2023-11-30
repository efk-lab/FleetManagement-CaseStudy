package com.mycomp.fleetmanager.mapper;

import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.document.Vehicle;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetVehicleResponse;
import com.mycomp.fleetmanager.model.SaveVehicleRequest;
import com.mycomp.fleetmanager.model.SaveVehicleResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VehicleMapper extends BaseMapper {
	
	public Vehicle toVehicle(SaveVehicleRequest saveVehicleRequest) {

		log.info("Mapping SaveVehicleRequest to Vehicle. SaveVehicleRequest: " + saveVehicleRequest.toString() + " User:" + userService.getUser());

		Vehicle vehicle = new Vehicle();
		vehicle.setPlate(saveVehicleRequest.getPlate());

		return vehicle;
	}

	public SaveVehicleResponse toSaveVehicleResponse(Vehicle vehicle) {

		log.info("Mapping Vehicle to SaveVehicleResponse. Vehicle: " + vehicle.toString() + " User:" + userService.getUser());

		SaveVehicleResponse saveVehicleResponse = new SaveVehicleResponse();
		saveVehicleResponse = (SaveVehicleResponse)toBaseResponse(saveVehicleResponse, vehicle);
		saveVehicleResponse.setVehicleId(vehicle.getVehicleId());
		saveVehicleResponse.setPlate(vehicle.getPlate());

		return saveVehicleResponse;
	}
	
	public GetVehicleResponse toGetVehicleResponse(Vehicle vehicle) {
		
		if(vehicle == null) {
			throw new FleetManagerException("Vehicle not found.");
		}
		
		log.info("Mapping Vehicle to GetVehicleResponse. Vehicle: " + vehicle.toString() + " User:" + userService.getUser());
		
		GetVehicleResponse getVehicleResponse = new GetVehicleResponse();
		getVehicleResponse = (GetVehicleResponse)toBaseResponse(getVehicleResponse, vehicle);
		getVehicleResponse.setVehicleId(vehicle.getVehicleId());
		getVehicleResponse.setPlate(vehicle.getPlate());

		return getVehicleResponse;
		
	}
	
}
