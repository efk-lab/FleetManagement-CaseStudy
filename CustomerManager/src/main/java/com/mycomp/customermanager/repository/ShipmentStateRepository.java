package com.mycomp.customermanager.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mycomp.customermanager.document.ShipmentState;

@Repository
public interface ShipmentStateRepository extends MongoRepository<ShipmentState, ObjectId> {
	
	List<ShipmentState> findByShipmentId(String shipmentId);

}
