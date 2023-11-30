package com.mycomp.fleetmanager.document;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "deliverypoint")
@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryPoint extends BaseDocument implements Serializable {

	private static final long serialVersionUID = 4234989823481930125L;

	@Id
	private ObjectId deliveryPointId;

	@Field
	private String deliveryPointName;

	@Field
	private int deliveryPointValue;

}
