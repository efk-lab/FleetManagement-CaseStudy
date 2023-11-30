package com.mycomp.vehiclemanager.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class GetDistributionRequest implements Serializable {
	
	private static final long serialVersionUID = -2315422287266626658L;
	
	private String distributionId;

}
