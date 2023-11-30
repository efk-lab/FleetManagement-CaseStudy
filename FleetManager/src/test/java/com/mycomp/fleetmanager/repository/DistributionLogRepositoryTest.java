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
import com.mycomp.fleetmanager.document.DistributionLog;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class DistributionLogRepositoryTest {
	
	@Autowired
	private DistributionLogRepository distributionLogRepository;

	@BeforeEach
	public void setUp() {
		distributionLogRepository.deleteAll();
	}

	@Test
	public void getDistributionLog() {

		DistributionLog distributionLog = prepareDistributionLog();
		
		DistributionLog savedDistributionLog = distributionLogRepository.save(distributionLog);
		DistributionLog foundDistributionLog = distributionLogRepository.findByDistributionId(savedDistributionLog.getDistributionId());
		
		assertThat(savedDistributionLog.getPlate()).isEqualTo(foundDistributionLog.getPlate());

	}
	
	private DistributionLog prepareDistributionLog() {
		
		DistributionLog distributionLog = new DistributionLog();
		
		distributionLog.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		distributionLog.setPlate("34 TL 34");
	
		return distributionLog;
		
	}
	
}
