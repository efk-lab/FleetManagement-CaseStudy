package com.mycomp.fleetmanager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mycomp.fleetmanager.document.ShipmentState;

@Repository
public interface ShipmentStateRepository extends MongoRepository<ShipmentState, ObjectId> {

}
