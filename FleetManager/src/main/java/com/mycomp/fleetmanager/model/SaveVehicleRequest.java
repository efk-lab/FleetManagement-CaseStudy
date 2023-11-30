package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SaveVehicleRequest implements Serializable {

	private static final long serialVersionUID = -5607336884134868734L;

	@NotNull
	@NotBlank
	@NotEmpty
	private String plate;

}
