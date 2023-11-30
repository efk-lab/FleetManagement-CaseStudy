package com.mycomp.vehiclemanager.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = -8583988220190816634L;

	private String createdBy;

	private ZonedDateTime creationDate;

	private String modifiedBy;

	private ZonedDateTime modificationDate;
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ZonedDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ZonedDateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(ZonedDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

}
