package com.mycomp.fleetmanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.fleetmanager.conf.security.UserService;

public abstract class BaseControllerImpl {
	
	@Autowired
	protected UserService userService;
	
}
