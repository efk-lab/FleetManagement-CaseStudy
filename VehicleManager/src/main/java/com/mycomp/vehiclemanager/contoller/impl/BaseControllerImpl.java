package com.mycomp.vehiclemanager.contoller.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.vehiclemanager.conf.security.server.UserService;

public abstract class BaseControllerImpl {
	
	@Autowired
	protected UserService userService;
	
}
