package com.mycomp.fleetmanager.document;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mycomp.fleetmanager.constant.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "user")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseDocument implements Serializable {

	private static final long serialVersionUID = -447469320245203153L;

	@Id
	private ObjectId userId;

	@Field
	private String email;

	@Field
	private String password;

	@Field
	private Role role;

}
