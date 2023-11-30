package com.mycomp.fleetmanager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mycomp.fleetmanager.conf.mongodb.MongoDBTestConfiguration;
import com.mycomp.fleetmanager.document.DeliveryLocation;
import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.document.Distribution;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.document.Vehicle;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class DistributionRepositoryTest {

	@Autowired
	private DistributionRepository distributionRepository;

	@BeforeEach
	public void setUp() {
		distributionRepository.deleteAll();
	}

	@Test
	public void saveDistribution() {

		Distribution distribution = prepareDistribution() ;

		Distribution savedDistribution = distributionRepository.save(distribution);
		Distribution foundDistribution = distributionRepository.findById(distribution.getDistributionId()).get();
		
		assertThat(savedDistribution.getDistributionId()).isEqualTo(foundDistribution.getDistributionId());

	}
	
	private Distribution prepareDistribution() {

		Distribution distribution = new Distribution();
		
		distribution.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		Vehicle vehicle = new Vehicle();
		vehicle.setPlate("34 TL 34");
		vehicle.setVehicleId(new ObjectId("62d322ddf9f5e01864bed242"));
		distribution.setPlate(vehicle);
		List<DeliveryLocation>  deliveryLocationList = new ArrayList<DeliveryLocation>();
		DeliveryLocation deliveryLocation = new DeliveryLocation();
		DeliveryPoint deliveryPoint = new DeliveryPoint();
		deliveryPoint.setDeliveryPointId(new ObjectId("62d322ddf9f5e01864bed242"));
		deliveryPoint.setDeliveryPointName("Transfer Center");
		deliveryLocation.setDeliveryPoint(deliveryPoint);
		List<Shipment> shipmentList = new ArrayList<Shipment>();
		Shipment shipment = new Shipment();
		shipment.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		shipment.setBarcode("P8988000125");
		shipment.setShipmentStatus(0);
		shipmentList.add(shipment);
		deliveryLocation.setShipments(shipmentList);
		deliveryLocationList.add(deliveryLocation);
		distribution.setRoute(deliveryLocationList);
		distribution.setCreatedBy("eda@gmail.com");
		distribution.setCreationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
		distribution.setModifiedBy("eda@gmail.com");
		distribution.setModificationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
		
		return distribution;

	}
	
}
