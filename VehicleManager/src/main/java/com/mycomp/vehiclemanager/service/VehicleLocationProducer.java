package com.mycomp.vehiclemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.mycomp.vehiclemanager.event.VehicleLocationEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleLocationProducer {
	
	@Value("${kafka.producer.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, VehicleLocationEvent> kafkaTemplate;
	

	public void produceVehicleLocation(String plate, VehicleLocationEvent vehicleLocationEvent) {

		ListenableFuture<SendResult<String, VehicleLocationEvent>> future = kafkaTemplate.send(topicName, plate, vehicleLocationEvent);

		future.addCallback(new KafkaSendCallback<String, VehicleLocationEvent>() {

			@Override
			public void onFailure(KafkaProducerException ex) {
				log.warn("VehicleLocationEvent could not be delivered. " + ex.getMessage());
			}

			@Override
			public void onSuccess(SendResult<String, VehicleLocationEvent> result) {
				log.info("VehicleLocationEvent was delivered with following offset: " + result.getRecordMetadata().offset() + " Plate: "
						+ result.getProducerRecord().value().getPlate() + " DistributionId: " + result.getProducerRecord().value().getDistributionId());

			}
		});

	}

	 
	
}
