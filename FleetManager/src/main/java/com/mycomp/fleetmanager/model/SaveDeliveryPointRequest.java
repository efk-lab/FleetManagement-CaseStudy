package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SaveDeliveryPointRequest implements Serializable {

	private static final long serialVersionUID = -2700989379059912562L;

	@NotNull
	@NotBlank
	@NotEmpty
	private String deliveryPointName;

	@Min(value = 1)
	@Max(value = 3)
	private int deliveryPointValue;

}
