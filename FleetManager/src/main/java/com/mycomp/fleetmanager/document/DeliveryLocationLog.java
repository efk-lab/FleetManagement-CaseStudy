package com.mycomp.fleetmanager.document;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class DeliveryLocationLog implements Serializable {

	private static final long serialVersionUID = -8205330873860954161L;

	@Field
	private int deliveryPoint;

	@Field
	private List<String> deliveries;
	
}
