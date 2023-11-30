package com.mycomp.customermanager.event;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ShipmentEvent implements Serializable {

	private static final long serialVersionUID = 7193111234636743732L;

	private String shipmentId;
	
	private String barcode;
	
	private String shipmentType;

	private String shipmentEventType;

	private String shipmentStatus;

	private Date eventDate;
	
}
