package com.mycomp.fleetmanager.document;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "distributionLog")
@Data
@EqualsAndHashCode(callSuper = false)
public class DistributionLog extends BaseDocument implements Serializable {

	private static final long serialVersionUID = 7407462745203135864L;

	@Id
	private ObjectId distributionLogId;

	@Field
	private ObjectId distributionId;

	@Field
	private String plate;

	@Field
	private List<DeliveryLocationLog> route;

}
