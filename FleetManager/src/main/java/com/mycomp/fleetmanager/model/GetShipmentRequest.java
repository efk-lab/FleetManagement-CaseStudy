package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GetShipmentRequest implements Serializable {

	private static final long serialVersionUID = -7538300530862105343L;

	@NotNull
	@NotBlank
	@NotEmpty
	private String barcode;

}
