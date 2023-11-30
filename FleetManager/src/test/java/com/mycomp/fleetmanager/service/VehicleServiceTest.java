package com.mycomp.fleetmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.document.Vehicle;
import com.mycomp.fleetmanager.mapper.VehicleMapper;
import com.mycomp.fleetmanager.model.SaveVehicleRequest;
import com.mycomp.fleetmanager.model.SaveVehicleResponse;
import com.mycomp.fleetmanager.repository.VehicleRepository;
import com.mycomp.fleetmanager.validator.VehicleValidator;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

	@Mock
	private VehicleRepository vehicleRepository;

	@Mock
	private UserService userService;

	@Mock
	private VehicleMapper vehicleMapper;

	@Mock
	private VehicleValidator vehicleValidator;

	@InjectMocks
	private VehicleService vehicleService;

	@Test
	public void saveVehicle() {
		
		SaveVehicleRequest saveVehicleRequest = prepareSaveVehicleRequest();
		SaveVehicleResponse saveVehicleResponseExpected = prepareSaveVehicleResponse();
		Vehicle vehicle = prepareVehicle();

		doNothing().when(vehicleValidator).validateSaveVehicleRequest(saveVehicleRequest);
		given(vehicleMapper.toVehicle(saveVehicleRequest)).willReturn(vehicle);
		given(vehicleRepository.save(vehicle)).willReturn(vehicle);
		given(vehicleMapper.toSaveVehicleResponse(vehicle)).willReturn(saveVehicleResponseExpected);

		SaveVehicleResponse saveVehicleResponseActual = vehicleService.saveVehicle(saveVehicleRequest);

		assertThat(saveVehicleResponseActual.getPlate()).isEqualTo(saveVehicleResponseExpected.getPlate());

	}

	private SaveVehicleRequest prepareSaveVehicleRequest() {

		SaveVehicleRequest saveVehicleRequest = new SaveVehicleRequest();

		saveVehicleRequest.setPlate("34 TL 34");

		return saveVehicleRequest;

	}

	private SaveVehicleResponse prepareSaveVehicleResponse() {
		
		SaveVehicleResponse saveVehicleResponse = new SaveVehicleResponse();

		saveVehicleResponse.setVehicleId(new ObjectId("62d322ddf9f5e01864bed242"));
		saveVehicleResponse.setPlate("34 TL 34");
//		saveVehicleResult.setCreatedBy("eda@gmail.com");
//		saveVehicleResult.setCreationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
//		saveVehicleResult.setModifiedBy("eda@gmail.com");
//		saveVehicleResult.setModificationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));

		return saveVehicleResponse;

	}

	private Vehicle prepareVehicle() {
		
		Vehicle vehicle = new Vehicle();

		vehicle.setVehicleId(new ObjectId("62d322ddf9f5e01864bed242"));
		vehicle.setPlate("34 TL 34");
//		vehicle.setCreatedBy("eda@gmail.com");
//		vehicle.setCreationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
//		vehicle.setModifiedBy("eda@gmail.com");
//		vehicle.setModificationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));

		return vehicle;
		
	}

}
