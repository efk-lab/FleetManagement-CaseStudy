package com.mycomp.vehiclemanager.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycomp.vehiclemanager.error.VehicleManagerException;
import com.mycomp.vehiclemanager.model.SignUpRequest;
import com.mycomp.vehiclemanager.model.SignUpResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/vehiclemanager/registry")
public interface UserRegistryController {
	
	@RequestMapping(
			value = "/sign-up", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(
	        value = "SignUp User",
	        notes = "Returns HTTP 404 or 500 if the user not signed up.")
	@ApiResponses({
            @ApiResponse(code = 200, message = "User signed up"),
            @ApiResponse(code = 404, message = "User not signed up"),
            @ApiResponse(code = 500, message = "Internal server error")})
	public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws VehicleManagerException;

}
