package com.mycomp.customermanager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mycomp.customermanager.conf.mongodb.MongoDBTestConfiguration;
import com.mycomp.customermanager.document.User;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	

	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
	}


	@Test
	public void saveUser() {

		User user = prepareUser();

		User savedUser = userRepository.save(user);
		User foundUser = userRepository.findById(savedUser.getUserId()).get();
		
		assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());

	}
	

	@Test
	public void findByEmail() {

		User user = prepareUser();

		User savedUser = userRepository.save(user);
		User foundUser = userRepository.findByEmail("xxx@gmail.com").get();
		
		assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());

	}
	
	private User prepareUser() {
		
		User user = new User();
		
		user.setUserId(new ObjectId("62d322ddf9f5e01864bed242"));
		user.setEmail("xxx@gmail.com");
		
		return user;
		
	}
	
}