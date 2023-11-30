package com.mycomp.fleetmanager.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssignPackageToBagResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -1053934476285714212L;

	private String packageBarcode;

	private String bagBarcode;

}
