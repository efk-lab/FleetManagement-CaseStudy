package com.mycomp.fleetmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.mycomp.fleetmanager.event.ShipmentEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShipmentEventProducerService {

	@Value("${kafka.producer.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, ShipmentEvent> kafkaTemplate;
	

	public void produceShipmentEvent(ShipmentEvent shipmentEvent) {

		ListenableFuture<SendResult<String, ShipmentEvent>> future = kafkaTemplate.send(topicName, shipmentEvent);

		future.addCallback(new KafkaSendCallback<String, ShipmentEvent>() {

			@Override
			public void onFailure(KafkaProducerException ex) {
				log.warn("ShipmentEvent could not be delivered. " + ex.getMessage());
			}

			@Override
			public void onSuccess(SendResult<String, ShipmentEvent> result) {
				log.info("ShipmentEvent was delivered with following offset: " + result.getRecordMetadata().offset() + " ShipmentId: "
						+ result.getProducerRecord().value().getShipmentId() + " Barcode: " + result.getProducerRecord().value().getBarcode());

			}
		});

	}

}
