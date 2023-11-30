package com.mycomp.customermanager.dto;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder
public class ShipmentStateDto implements Serializable {

	private static final long serialVersionUID = 3970723611601575930L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId shipmentStateId;
	
	private String barcode;
	
	private String shipmentType;

	private String shipmentEventType;

	private String shipmentStatus;

	private Date eventDate; 
}
