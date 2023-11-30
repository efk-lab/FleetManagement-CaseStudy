package com.mycomp.fleetmanager.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mycomp.fleetmanager.dto.DeliveryLocationDto;

import lombok.Data;

@Data
public class SaveDistributionRequest implements Serializable {

	private static final long serialVersionUID = 6948047208026523280L;

	@NotNull
	@NotBlank
	@NotEmpty
	private String plate;

	@NotNull
	@NotEmpty
	@Size(min=1)
	private List<DeliveryLocationDto> route;

}
