package com.mycomp.customermanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.customermanager.conf.security.UserService;

public abstract class BaseControllerImpl {
	
	@Autowired
	protected UserService userService;
	
}
