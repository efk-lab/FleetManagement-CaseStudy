package com.mycomp.customermanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.customermanager.conf.security.UserService;
import com.mycomp.customermanager.document.BaseDocument;
import com.mycomp.customermanager.model.BaseResponse;

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
