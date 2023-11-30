package com.mycomp.fleetmanager.dto;

import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.document.Distribution;
import com.mycomp.fleetmanager.document.Shipment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckDeliveryDto {
	
	private DeliveryPoint messageDeliveryPoint;
	
	private Distribution distribution;
	
	private Shipment shipment;

}
