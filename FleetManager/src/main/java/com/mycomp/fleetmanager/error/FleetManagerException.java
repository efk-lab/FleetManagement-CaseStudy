package com.mycomp.fleetmanager.error;

public class FleetManagerException extends RuntimeException {

	private static final long serialVersionUID = 3560654528167410058L;
	
	
	public FleetManagerException(String exception) {
        super(exception);
    }

}
