package com.mycomp.fleetmanager.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DeliveryLocationLogDto implements Serializable {

	private static final long serialVersionUID = 399124604133083405L;

	private int deliveryPoint;

	private List<DeliveryLogDto> deliveries;
	
}
