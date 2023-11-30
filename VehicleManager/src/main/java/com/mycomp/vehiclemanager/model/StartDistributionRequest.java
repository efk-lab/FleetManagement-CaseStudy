package com.mycomp.vehiclemanager.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class StartDistributionRequest implements Serializable {

	private static final long serialVersionUID = -113131942887011400L;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String plate;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String distributionId;

}
