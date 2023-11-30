package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SaveVehicleResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 7682272050002057396L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId vehicleId;

	private String plate;

}
