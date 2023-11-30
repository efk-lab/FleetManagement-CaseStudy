package com.mycomp.fleetmanager.document;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "distribution")
@Data
@EqualsAndHashCode(callSuper = false)
public class Distribution extends BaseDocument implements Serializable {

	private static final long serialVersionUID = 8262405724069988000L;

	@Id
	private ObjectId distributionId;

	@DBRef
	private Vehicle plate;

	@Field
	private List<DeliveryLocation> route;
	
}
