package com.mycomp.fleetmanager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mycomp.fleetmanager.conf.mongodb.MongoDBTestConfiguration;
import com.mycomp.fleetmanager.document.ShipmentState;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class ShipmentStateRepositoryTest {
	
	@Autowired
	private ShipmentStateRepository shipmentStateRepository;

	@BeforeEach
	public void setUp() {
		shipmentStateRepository.deleteAll();
	}

	@Test
	public void saveShipmentState() {

		ShipmentState shipmentState = prepareShipmentState();

		ShipmentState savedShipmentState = shipmentStateRepository.save(shipmentState);
		ShipmentState foundShipmentState = shipmentStateRepository.findById(savedShipmentState.getShipmentEventId()).get();

		assertThat(savedShipmentState.getBarcode()).isEqualTo(foundShipmentState.getBarcode());

	}

	private ShipmentState prepareShipmentState() {
		
		ShipmentState shipmentState = new ShipmentState();

		shipmentState.setShipmentId("62d322ddf9f5e01864bed242");
		shipmentState.setBarcode("P8988000125");
		
		return shipmentState;
		
	}

}
