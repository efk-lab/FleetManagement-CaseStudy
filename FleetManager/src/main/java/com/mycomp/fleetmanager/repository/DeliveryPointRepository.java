package com.mycomp.fleetmanager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mycomp.fleetmanager.document.DeliveryPoint;

@Repository
public interface DeliveryPointRepository extends MongoRepository<DeliveryPoint, ObjectId> {
	
	DeliveryPoint findByDeliveryPointName(String deliveryPointName);
	
	DeliveryPoint findByDeliveryPointValue(int deliveryPointValue);
	
}
