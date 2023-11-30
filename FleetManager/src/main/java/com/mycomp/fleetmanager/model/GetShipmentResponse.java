package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mycomp.fleetmanager.constant.ShipmentStatus;
import com.mycomp.fleetmanager.constant.ShipmentType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetShipmentResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 3559604021969977086L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId shipmentId;

	private String barcode;
	
	private String bagBarcode;

	private String deliveryPoint;

	private ShipmentType shipmentType;

	private ShipmentStatus shipmentStatus;

	private double volumetricWeight;

}
