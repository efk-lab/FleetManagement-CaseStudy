package com.mycomp.fleetmanager.document;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "shipment")
@Data
@EqualsAndHashCode(callSuper = false)
public class Shipment extends BaseDocument implements Serializable {

	private static final long serialVersionUID = 8047562126952256622L;

	@Id
	private ObjectId shipmentId;

	@Field
	private String barcode;
	
	@Field
	private String bagBarcode;

	@DBRef
	private DeliveryPoint deliveryPoint;

	@Field
	private double volumetricWeight;

	@Field
	private int shipmentType;

	@Field
	private int shipmentStatus;

}
