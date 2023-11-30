package com.mycomp.fleetmanager.document;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class DeliveryLog implements Serializable {

	private static final long serialVersionUID = -8605639339848651143L;

	@Field
	private String barcode;

}
