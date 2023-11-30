package com.mycomp.fleetmanager.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeliveryStatusDto implements Serializable {

	private static final long serialVersionUID = -8726408157624319331L;

	private String barcode;

	private int status;

}
