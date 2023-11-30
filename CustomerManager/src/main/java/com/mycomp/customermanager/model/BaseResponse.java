package com.mycomp.customermanager.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class BaseResponse implements Serializable {

	private static final long serialVersionUID = -8583988220190816634L;

	private String createdBy;

	private ZonedDateTime creationDate;

	private String modifiedBy;

	private ZonedDateTime modificationDate;

}
