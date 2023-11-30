package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SaveDeliveryPointResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 7154030693939472456L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId deliveryPointId;

	private String deliveryPoint;

}
