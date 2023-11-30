package com.mycomp.vehiclemanager.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GetVehicleRequest {
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String plate;

}
