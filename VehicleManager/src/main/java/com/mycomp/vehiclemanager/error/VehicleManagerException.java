package com.mycomp.vehiclemanager.error;

public class VehicleManagerException extends RuntimeException {

	private static final long serialVersionUID = 3560654528167410058L;
	
	public VehicleManagerException(String exception) {
        super(exception);
    }

}
