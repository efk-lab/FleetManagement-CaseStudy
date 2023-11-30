package com.mycomp.fleetmanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.document.BaseDocument;
import com.mycomp.fleetmanager.model.BaseResponse;

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
