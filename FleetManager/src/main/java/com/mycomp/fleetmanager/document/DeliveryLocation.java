package com.mycomp.fleetmanager.document;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;

@Data
public class DeliveryLocation implements Serializable {

	private static final long serialVersionUID = 392095967389008940L;

	@DBRef
	private DeliveryPoint deliveryPoint;

	@DBRef
	private List<Shipment> shipments;
	
}
