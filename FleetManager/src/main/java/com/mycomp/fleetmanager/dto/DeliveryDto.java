package com.mycomp.fleetmanager.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeliveryDto implements Serializable {

	private static final long serialVersionUID = 5241105602515156981L;

	private String barcode;

}
