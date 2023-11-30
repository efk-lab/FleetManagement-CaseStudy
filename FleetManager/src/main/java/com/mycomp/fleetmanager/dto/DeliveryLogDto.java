package com.mycomp.fleetmanager.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeliveryLogDto implements Serializable {

	private static final long serialVersionUID = 6238000042328499133L;

	private String barcode;

}
