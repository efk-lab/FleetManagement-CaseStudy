package com.mycomp.vehiclemanager.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetDistributionResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 883807739200039335L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId distributionId;

	private String plate;


}
