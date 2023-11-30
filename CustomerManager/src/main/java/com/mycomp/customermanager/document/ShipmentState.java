package com.mycomp.customermanager.document;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "shipmentState")
@Data
@EqualsAndHashCode(callSuper = false)
public class ShipmentState extends BaseDocument implements Serializable {

	private static final long serialVersionUID = 3430261046007397456L;

	@Id
	private ObjectId shipmentStateId;

	@Field
	private String shipmentId;
	
	@Field
	private String barcode;
	
	@Field
	private String shipmentType;

	@Field
	private String shipmentEventType;

	@Field
	private String shipmentStatus;

	@Field
	private Date eventDate;
	
}
