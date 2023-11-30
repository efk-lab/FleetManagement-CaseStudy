package com.mycomp.vehiclemanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.vehiclemanager.conf.security.server.UserService;
import com.mycomp.vehiclemanager.document.BaseDocument;
import com.mycomp.vehiclemanager.model.BaseResponse;

public abstract class BaseMapper {

	@Autowired
	protected UserService userService;


	protected BaseResponse toBaseResponse(BaseResponse baseResponse, BaseDocument baseDocument) {

		baseResponse.setCreatedBy(baseDocument.getCreatedBy());
		baseResponse.setCreationDate(baseDocument.getCreationDate());
		baseResponse.setModifiedBy(baseDocument.getModifiedBy());
		baseResponse.setModificationDate(baseDocument.getModificationDate());

		return baseResponse;
	}

}
