package com.mycomp.customermanager.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mycomp.customermanager.document.ShipmentState;
import com.mycomp.customermanager.dto.ShipmentStateDto;
import com.mycomp.customermanager.event.ShipmentEvent;
import com.mycomp.customermanager.model.GetShipmentStateResponse;

@Component
public class ShipmentStateMapper {
	
	public ShipmentState toShipmentState(ShipmentEvent shipmentEvent) {
		
		ShipmentState shipmentState = new ShipmentState();
		
		shipmentState.setShipmentId(shipmentEvent.getShipmentId());
		shipmentState.setBarcode(shipmentEvent.getBarcode());
		shipmentState.setShipmentType(shipmentEvent.getShipmentType());
		shipmentState.setShipmentStatus(shipmentEvent.getShipmentStatus());
		shipmentState.setShipmentEventType(shipmentEvent.getShipmentEventType());
		shipmentState.setEventDate(shipmentEvent.getEventDate());
		
		return shipmentState;
		
	}
	
	public GetShipmentStateResponse toGetShipmentStateResponse(List<ShipmentState> shipmentStates){
		
		GetShipmentStateResponse getShipmentStateResponse = new GetShipmentStateResponse();
		List<ShipmentStateDto> shipmentStateDtoList =  new ArrayList<>();
	
		shipmentStates.stream()
		.forEach(shipmentState -> shipmentStateDtoList.add(
				ShipmentStateDto.builder()
					   			  .shipmentStateId(shipmentState.getShipmentStateId())
					   			  .barcode(shipmentState.getBarcode())
					   			  .shipmentType(shipmentState.getShipmentType())
					   			  .shipmentStatus(shipmentState.getShipmentStatus())
					   			  .shipmentEventType(shipmentState.getShipmentEventType())
					   			  .eventDate(shipmentState.getEventDate())
					   			  .build()			   
				));
		
		getShipmentStateResponse.setShipmentStateDtoList(shipmentStateDtoList);
		
		return getShipmentStateResponse;
		
	}
}
