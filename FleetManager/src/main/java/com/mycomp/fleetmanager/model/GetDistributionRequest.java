package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class GetDistributionRequest implements Serializable {

	private static final long serialVersionUID = -5827469898805142036L;

	private String distributionId;

}
