package com.mycomp.customermanager.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GetShipmentStateRequest {
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String shipmentId;

}
