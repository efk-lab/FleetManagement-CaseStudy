package com.mycomp.fleetmanager.document;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mycomp.fleetmanager.constant.ShipmentEventType;
import com.mycomp.fleetmanager.constant.ShipmentStatus;
import com.mycomp.fleetmanager.constant.ShipmentType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "shipmentState")
@Data
@EqualsAndHashCode(callSuper = false)
public class ShipmentState implements Serializable {

	private static final long serialVersionUID = 3430261046007397456L;

	@Id
	private ObjectId shipmentEventId;

	@Field
	private String shipmentId;
	
	@Field
	private String barcode;
	
	@Field
	private ShipmentType shipmentType;

	@Field
	private ShipmentEventType shipmentEventType;

	@Field
	private ShipmentStatus shipmentStatus;

	@Field
	private Date eventDate;
	
	@Field
	private String createdBy;
	
	@Field
	private Date creationDate;
}
