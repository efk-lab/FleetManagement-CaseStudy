package com.mycomp.vehiclemanager.model;

import java.io.Serializable;

import com.mycomp.vehiclemanager.constant.DistributionStatus;

import lombok.Data;

@Data
public class StartDistributionResponse implements Serializable { 
	
    private static final long serialVersionUID = -3868703160251954545L;

	private String plate;
	
	private String distributionId;

	private DistributionStatus distributionStatus;

}
