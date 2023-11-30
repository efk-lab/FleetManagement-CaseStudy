package com.mycomp.fleetmanager.event;

import java.io.Serializable;

import lombok.Data;

@Data
public class VehicleLocationEvent implements Serializable {

	private static final long serialVersionUID = 6367670278299030985L;

	private String distributionId;

	private int deliveryPoint;

	private double latitude;

	private double longitude;

}
