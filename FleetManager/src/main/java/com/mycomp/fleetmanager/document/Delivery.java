package com.mycomp.fleetmanager.document;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class Delivery implements Serializable {

	private static final long serialVersionUID = 7278538270784527030L;

	@Field
	private String barcode;

	@Field
	private int status;

}
