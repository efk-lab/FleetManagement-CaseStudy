package com.mycomp.vehiclemanager.event;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class VehicleLocationEvent implements Serializable {

	private static final long serialVersionUID = 6129981059111468424L;
	
	private String plate;
	
	private String distributionId;
	
	private int deliveryPoint;
	
	private double latitude;
	
	private double longitude;
	
}
