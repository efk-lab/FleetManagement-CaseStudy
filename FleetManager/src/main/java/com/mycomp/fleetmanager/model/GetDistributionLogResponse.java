package com.mycomp.fleetmanager.model;


import java.io.Serializable;
import java.util.List;

import com.mycomp.fleetmanager.dto.DeliveryLocationLogDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetDistributionLogResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -4818591302575842080L;

	private String plate;

	private List<DeliveryLocationLogDto> route;

}
