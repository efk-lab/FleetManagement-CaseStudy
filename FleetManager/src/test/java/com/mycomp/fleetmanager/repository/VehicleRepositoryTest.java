package com.mycomp.fleetmanager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mycomp.fleetmanager.conf.mongodb.MongoDBTestConfiguration;
import com.mycomp.fleetmanager.document.Vehicle;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class VehicleRepositoryTest {
	
	@Autowired
	private VehicleRepository vehicleRepository;

	@BeforeEach
	public void setUp() {
		vehicleRepository.deleteAll();
	}

	@Test
	public void saveVehicle() {

		Vehicle vehicle = prepareVehicle();

		Vehicle savedVehicle = vehicleRepository.save(vehicle);
		Vehicle foundVehicle = vehicleRepository.findById(savedVehicle.getVehicleId()).get();
		
		assertThat(savedVehicle.getPlate()).isEqualTo(foundVehicle.getPlate());

	}
	
	@Test
	public void findVehicleByPlate() {
		
		Vehicle vehicle = prepareVehicle();
		
		Vehicle savedVehicle = vehicleRepository.save(vehicle);
		Vehicle foundVehicle = vehicleRepository.findByPlate("34 TL 34");
		assertThat(savedVehicle.getPlate()).isEqualTo(foundVehicle.getPlate());

	}
	
	private Vehicle prepareVehicle() {
		
		Vehicle vehicle = new Vehicle();
		
		vehicle.setVehicleId(new ObjectId("62d322ddf9f5e01864bed242"));
		vehicle.setPlate("34 TL 34");

		return vehicle;
	}
}
