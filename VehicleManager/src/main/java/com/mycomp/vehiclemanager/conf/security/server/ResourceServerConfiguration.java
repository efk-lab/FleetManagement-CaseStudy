package com.mycomp.vehiclemanager.conf.security.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.mycomp.vehiclemanager.constant.Role;
import com.mycomp.vehiclemanager.error.VehicleManagerAccessDeniedHandler;
import com.mycomp.vehiclemanager.error.VehicleManagerAuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private final VehicleManagerAuthenticationEntryPoint vehicleManagerAuthenticationEntryPoint;
	
	private final String RESOURCE_SERVER_RESOURCE_ID = "vehiclemanagerApi";
	
	
	public ResourceServerConfiguration(VehicleManagerAuthenticationEntryPoint vehicleManagerAuthenticationEntryPoint) {
		
		this.vehicleManagerAuthenticationEntryPoint = vehicleManagerAuthenticationEntryPoint;
		
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		
		resources.resourceId(RESOURCE_SERVER_RESOURCE_ID);
		
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.antMatcher("/vehiclemanager/**").authorizeRequests()
				.antMatchers("/vehiclemanager/registry/sign-up/**").permitAll()
				.antMatchers("/vehiclemanager/distribution/start-up/**").hasAnyAuthority(Role.VEHICLE_MNGR_ADMIN.name(), Role.VEHICLE_MNGR_USER.name())			
				.antMatchers("/vehiclemanager/**").authenticated().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(vehicleManagerAuthenticationEntryPoint)
				.accessDeniedHandler(new VehicleManagerAccessDeniedHandler());

	}
	
}
