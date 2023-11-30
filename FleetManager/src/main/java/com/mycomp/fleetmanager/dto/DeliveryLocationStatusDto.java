package com.mycomp.fleetmanager.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DeliveryLocationStatusDto implements Serializable {

	private static final long serialVersionUID = -7634713971179475987L;

	private int deliveryPoint;

	private List<DeliveryStatusDto> deliveries;

}
