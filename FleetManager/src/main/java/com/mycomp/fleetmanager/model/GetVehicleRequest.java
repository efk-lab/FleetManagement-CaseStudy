package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class GetVehicleRequest implements Serializable{
	
	private static final long serialVersionUID = 5273564963234782343L;
	
	private String plate;

}
