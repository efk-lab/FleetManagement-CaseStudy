package com.mycomp.customermanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.customermanager.document.ShipmentState;
import com.mycomp.customermanager.mapper.ShipmentStateMapper;
import com.mycomp.customermanager.model.GetShipmentStateRequest;
import com.mycomp.customermanager.model.GetShipmentStateResponse;
import com.mycomp.customermanager.repository.ShipmentStateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShipmentStateService extends BaseService {
	
	@Autowired
	private ShipmentStateRepository shipmentEventRepository;
	
	@Autowired
	private ShipmentStateMapper shipmentStateMapper;
	
	
	public GetShipmentStateResponse getShipmentState(GetShipmentStateRequest getShipmentStateRequest) {

		GetShipmentStateResponse getShipmentStateResponse = new GetShipmentStateResponse();

		List<ShipmentState> shipmentStates = shipmentEventRepository.findByShipmentId(getShipmentStateRequest.getShipmentId());
		log.info("ShipmentState retrieved. shipmentStates: " + shipmentStates.size());
		getShipmentStateResponse = shipmentStateMapper.toGetShipmentStateResponse(shipmentStates);

		log.info("ShipmentState retrieved. GetShipmentStateResponse: " + getShipmentStateResponse.toString() + " User:" + userService.getUser());

		return getShipmentStateResponse;

	}
	
}
