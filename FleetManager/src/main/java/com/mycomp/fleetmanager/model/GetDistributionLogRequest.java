package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class GetDistributionLogRequest implements Serializable {

	private static final long serialVersionUID = 2055445606271886341L;

	private ObjectId distributionId;

}
