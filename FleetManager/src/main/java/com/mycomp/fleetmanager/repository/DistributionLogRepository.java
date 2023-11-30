package com.mycomp.fleetmanager.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mycomp.fleetmanager.document.DistributionLog;

@Repository
public interface DistributionLogRepository extends MongoRepository<DistributionLog, ObjectId> {
	
	DistributionLog findByDistributionId(ObjectId distributionId);

	DistributionLog findByDistributionIdAndRouteDeliveryPoint(ObjectId distributionId, int deliveryPoint);
	
}
