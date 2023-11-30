package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AssignPackageToBagRequest implements Serializable {

	private static final long serialVersionUID = -5215967454588314946L;

	@NotNull
	@NotBlank
	@NotEmpty
	private String packageBarcode;

	@NotNull
	@NotBlank
	@NotEmpty
	private String bagBarcode;

}
