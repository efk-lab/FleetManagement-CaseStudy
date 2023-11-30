package com.mycomp.vehiclemanager.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.vehiclemanager.conf.security.server.UserService;

public abstract class BaseService {
	
	@Autowired
	protected UserService userService;
	
}
