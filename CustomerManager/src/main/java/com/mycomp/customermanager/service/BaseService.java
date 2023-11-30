package com.mycomp.customermanager.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycomp.customermanager.conf.security.UserService;

public abstract class BaseService {
	
	@Autowired
	protected UserService userService;
	
}
