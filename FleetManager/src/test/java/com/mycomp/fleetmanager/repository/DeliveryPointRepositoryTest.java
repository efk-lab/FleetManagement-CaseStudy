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

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class DeliveryPointRepositoryTest {
	
	@Autowired
	private DeliveryPointRepository deliveryPointRepository;

	@BeforeEach
	public void setUp() {
		deliveryPointRepository.deleteAll();
	}
	
	@Test
	public void saveDeliveryPoint() {

		DeliveryPoint deliveryPoint = prepareDeliveryPoint();

		DeliveryPoint savedDeliveryPoint = deliveryPointRepository.save(deliveryPoint);
		DeliveryPoint foundDeliveryPoint = deliveryPointRepository.findById(savedDeliveryPoint.getDeliveryPointId()).get();
		
		assertThat(savedDeliveryPoint.getDeliveryPointName()).isEqualTo(foundDeliveryPoint.getDeliveryPointName());

	}
	
	@Test
	public void findDeliveryPointByName() {

		DeliveryPoint deliveryPoint = prepareDeliveryPoint();

		DeliveryPoint savedDeliveryPoint = deliveryPointRepository.save(deliveryPoint);
		DeliveryPoint foundDeliveryPoint = deliveryPointRepository.findByDeliveryPointName(savedDeliveryPoint.getDeliveryPointName());
		
		assertThat(savedDeliveryPoint.getDeliveryPointName()).isEqualTo(foundDeliveryPoint.getDeliveryPointName());

	}
	

	@Test
	public void findDeliveryPointByValue() {

		DeliveryPoint deliveryPoint = prepareDeliveryPoint();

		DeliveryPoint savedDeliveryPoint = deliveryPointRepository.save(deliveryPoint);
		DeliveryPoint foundDeliveryPoint = deliveryPointRepository.findByDeliveryPointValue(savedDeliveryPoint.getDeliveryPointValue());
		
		assertThat(savedDeliveryPoint.getDeliveryPointName()).isEqualTo(foundDeliveryPoint.getDeliveryPointName());

	}
	
	private DeliveryPoint prepareDeliveryPoint() {
		
		DeliveryPoint deliveryPoint = new DeliveryPoint();
		
		deliveryPoint.setDeliveryPointId(new ObjectId("62d322ddf9f5e01864bed242"));
		deliveryPoint.setDeliveryPointName("Branch");
		deliveryPoint.setDeliveryPointValue(1);
		
		return deliveryPoint;
	}
	
}
