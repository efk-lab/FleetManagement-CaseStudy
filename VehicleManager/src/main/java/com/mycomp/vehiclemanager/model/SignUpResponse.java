package com.mycomp.vehiclemanager.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignUpResponse extends BaseResponse implements Serializable {
		
	private static final long serialVersionUID = 4981127810460394403L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId userId;
	
	private String email;

}
