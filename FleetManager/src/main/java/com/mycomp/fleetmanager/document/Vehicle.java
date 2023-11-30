package com.mycomp.fleetmanager.document;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "vehicle")
@Data
@EqualsAndHashCode(callSuper = false)
public class Vehicle extends BaseDocument implements Serializable {

	private static final long serialVersionUID = 3033160541303915452L;

	@Id
	private ObjectId vehicleId;

	@Field
	private String plate;
	
}
