package com.mycomp.customermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.mycomp.customermanager.event.ShipmentEvent;
import com.mycomp.customermanager.mapper.ShipmentStateMapper;
import com.mycomp.customermanager.repository.ShipmentStateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShipmentTrackerService extends BaseService {
	
	@Autowired
	private ShipmentStateRepository shipmentEventRepository;
	
	@Autowired
	private ShipmentStateMapper shipmentStateMapper;
	
	
	@KafkaListener(topics = "${kafka.consumer.topic.name}", containerFactory = "shipmentEventKafkaListenerContainerFactory")
	public void trackShipment(ShipmentEvent shipmentEvent) { 

		if (shipmentEvent != null) {
			processShipmentEvent(shipmentEvent);
		} else {
			log.error("Processing ShipmentEvent failed.");
		}

	}
	
	private void processShipmentEvent(ShipmentEvent shipmentEvent) {
		
		log.debug("Processing ShipmentEvent:" + shipmentEvent.toString());
		
		shipmentEventRepository.save(shipmentStateMapper.toShipmentState(shipmentEvent));
		
	}
	
}
