package com.mycomp.customermanager.conf.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.mycomp.customermanager.constant.Role;
import com.mycomp.customermanager.error.CustomerManagerAccessDeniedHandler;
import com.mycomp.customermanager.error.CustomerManagerAuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private final CustomerManagerAuthenticationEntryPoint customerManagerAuthenticationEntryPoint;
	
	private final String RESOURCE_SERVER_RESOURCE_ID = "customermanagerApi";
	

	public ResourceServerConfiguration(CustomerManagerAuthenticationEntryPoint customerManagerAuthenticationEntryPoint) {
		this.customerManagerAuthenticationEntryPoint = customerManagerAuthenticationEntryPoint;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_SERVER_RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.antMatcher("/customermanager/**").authorizeRequests()
				.antMatchers("/customermanager/registry/sign-up/**").permitAll()
				.antMatchers("/customermanager/shipment/state/**").hasAnyAuthority(Role.CUSTOMER_MNGR_ADMIN.name(), Role.CUSTOMER_MNGR_USER.name())
				.antMatchers("/customermanager/**").authenticated().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(customerManagerAuthenticationEntryPoint)
				.accessDeniedHandler(new CustomerManagerAccessDeniedHandler());

	}
}
