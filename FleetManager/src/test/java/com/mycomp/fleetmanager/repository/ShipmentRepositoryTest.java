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
import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.document.Shipment;


@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class ShipmentRepositoryTest {

	@Autowired
	private ShipmentRepository shipmentRepository;

	@BeforeEach
	public void setUp() {
		shipmentRepository.deleteAll();
	}

	@Test
	public void saveShipment() {

		Shipment shipment = prepareShipment();

		Shipment savedShipment = shipmentRepository.save(shipment);
		Shipment foundShipment = shipmentRepository.findById(savedShipment.getShipmentId()).get();
		
		assertThat(savedShipment.getBarcode()).isEqualTo(foundShipment.getBarcode());

	}
	
	@Test
	public void findByBarcode() {
		
		Shipment shipment = prepareShipment();

		Shipment savedShipment = shipmentRepository.save(shipment);
		Shipment foundShipment = shipmentRepository.findByBarcode(savedShipment.getBarcode());
		
		assertThat(savedShipment.getBarcode()).isEqualTo(foundShipment.getBarcode());

	}
	
	@Test
	public void findByBarcodeAndShipmentStatus() {
		
		Shipment shipment = prepareShipment();

		Shipment savedShipment = shipmentRepository.save(shipment);
		Shipment foundShipment = shipmentRepository.findByBarcodeAndShipmentStatus(savedShipment.getBarcode(), savedShipment.getShipmentStatus());
		
		assertThat(savedShipment.getBarcode()).isEqualTo(foundShipment.getBarcode());

	}
	
	private Shipment prepareShipment() {
		
		Shipment shipment = new Shipment();
		
		shipment.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		shipment.setBarcode("P8988000125");
		DeliveryPoint deliveryPoint = new DeliveryPoint();
		deliveryPoint.setDeliveryPointId(new ObjectId("62d322ddf9f5e01864bed242"));
		shipment.setDeliveryPoint(deliveryPoint);
		shipment.setShipmentType(1);
		shipment.setVolumetricWeight(10);

		return shipment;
		
	}
}
