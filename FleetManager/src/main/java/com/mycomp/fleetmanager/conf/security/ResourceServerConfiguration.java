package com.mycomp.fleetmanager.conf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.mycomp.fleetmanager.constant.Role;
import com.mycomp.fleetmanager.error.FleetManagerAccessDeniedHandler;
import com.mycomp.fleetmanager.error.FleetManagerAuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private final FleetManagerAuthenticationEntryPoint fleetManagerAuthenticationEntryPoint;
	
	private final String RESOURCE_SERVER_RESOURCE_ID = "fleetmanagerApi";

	public ResourceServerConfiguration(FleetManagerAuthenticationEntryPoint fleetManagerAuthenticationEntryPoint) {
		this.fleetManagerAuthenticationEntryPoint = fleetManagerAuthenticationEntryPoint;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_SERVER_RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.antMatcher("/fleetmanager/**").authorizeRequests()
				.antMatchers("/fleetmanager/registry/sign-up/**").permitAll()
				.antMatchers("/fleetmanager/vehicle/record/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name())
				.antMatchers("/fleetmanager/vehicle/details/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name(), Role.FLEET_MNGR_SYSTEM.name())
				.antMatchers("/fleetmanager/deliverypoint/record/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name())
				.antMatchers("/fleetmanager/shipment/package/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name())
				.antMatchers("/fleetmanager/shipment/bag/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name())
				.antMatchers("/fleetmanager/shipment/package-to-bag/**").hasAuthority(Role.FLEET_MNGR_USER.name())
				.antMatchers("/fleetmanager/distribution/record/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name())
				.antMatchers("/fleetManager/distribution/details/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name(), Role.FLEET_MNGR_SYSTEM.name())
				.antMatchers("/fleetManager/distributionlog/details/**").hasAnyAuthority(Role.FLEET_MNGR_ADMIN.name(), Role.FLEET_MNGR_USER.name())
				.antMatchers("/fleetmanager/**").authenticated().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(fleetManagerAuthenticationEntryPoint)
				.accessDeniedHandler(new FleetManagerAccessDeniedHandler());

	}
}
