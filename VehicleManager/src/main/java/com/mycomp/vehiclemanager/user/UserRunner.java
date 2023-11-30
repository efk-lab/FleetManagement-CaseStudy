package com.mycomp.vehiclemanager.user;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mycomp.vehiclemanager.constant.Role;
import com.mycomp.vehiclemanager.document.User;
import com.mycomp.vehiclemanager.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
public class UserRunner implements CommandLineRunner {

	@Value("${admin.user.email}")
	private String adminEmail;

	@Value("${admin.user.password}")
	private String adminPassword;

	@Value("${system.user.email}")
	private String systemEmail;

	@Value("${system.user.password}")
	private String systemPassword;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public void run(String... args) throws Exception {

		if(userRepository.findByEmail(adminEmail).isEmpty()) {
			User adminUser = new User();
			adminUser.setEmail(adminEmail);
			adminUser.setPassword(passwordEncoder.encode(adminPassword));
			adminUser.setRole(Role.VEHICLE_MNGR_ADMIN);
			adminUser.setCreatedBy(adminEmail);
			adminUser.setCreationDate(ZonedDateTime.now());
			userRepository.save(adminUser);
		}

		
		if(userRepository.findByEmail(systemEmail).isEmpty()) {

			User systemUser = new User();
			systemUser.setEmail(systemEmail);
			systemUser.setPassword(passwordEncoder.encode(systemPassword));
			systemUser.setRole(Role.VEHICLE_MNGR_SYSTEM);
			systemUser.setCreatedBy(systemEmail);
			systemUser.setCreationDate(ZonedDateTime.now());
			userRepository.save(systemUser);
		}


		log.info("Admin and System user saved.");
	}

}
