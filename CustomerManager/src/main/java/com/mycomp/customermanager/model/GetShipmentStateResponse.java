package com.mycomp.customermanager.model;

import java.io.Serializable;
import java.util.List;

import com.mycomp.customermanager.dto.ShipmentStateDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetShipmentStateResponse implements Serializable {

	private static final long serialVersionUID = 4926807422844926270L;
	
	private List<ShipmentStateDto> shipmentStateDtoList;
	
}
