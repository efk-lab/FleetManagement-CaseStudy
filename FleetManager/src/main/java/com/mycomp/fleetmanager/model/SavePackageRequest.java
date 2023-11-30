package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SavePackageRequest implements Serializable {

	private static final long serialVersionUID = -1157596721931753703L;

	@NotNull
	@NotBlank
	@NotEmpty
	private String barcode;

	@Min(value = 1)
	@Max(value = 3)
	private int deliveryPoint;

	private double volumetricWeight;

}
