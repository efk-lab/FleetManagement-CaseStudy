package com.mycomp.fleetmanager.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.fleetmanager.conf.security.UserService;

public abstract class BaseService {
	
	@Autowired
	protected UserService userService;
	
}
