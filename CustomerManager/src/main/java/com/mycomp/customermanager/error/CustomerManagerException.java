package com.mycomp.customermanager.error;

public class CustomerManagerException extends RuntimeException {

	private static final long serialVersionUID = 3560654528167410058L;
	
	
	public CustomerManagerException(String exception) {
        super(exception);
    }

}
