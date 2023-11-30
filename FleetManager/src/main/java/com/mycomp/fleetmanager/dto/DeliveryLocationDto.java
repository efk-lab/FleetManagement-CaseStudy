package com.mycomp.fleetmanager.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DeliveryLocationDto implements Serializable {

	private static final long serialVersionUID = -8656745420512998825L;

	private int deliveryPoint;

	private List<DeliveryDto> deliveries;

}
