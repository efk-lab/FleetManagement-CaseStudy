package com.mycomp.vehiclemanager.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable {

	private static final long serialVersionUID = -6742970019448105044L;

	@NotNull
	@NotBlank
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@NotBlank
	@NotEmpty
	private String password;
	
}
