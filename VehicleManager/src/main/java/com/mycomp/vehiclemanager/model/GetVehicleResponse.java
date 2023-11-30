package com.mycomp.vehiclemanager.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetVehicleResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -2274324625395431337L;

	private ObjectId vehicleId;
	
	private String plate;
	
}
