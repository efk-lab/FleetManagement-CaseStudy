package com.mycomp.fleetmanager.conf.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mycomp.fleetmanager.error.FleetManagerAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class ServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final String SWAGGER_URI_API_DOC = "/v2/api-docs";
	private final String SWAGGER_URI_CONF_UI = "/configuration/ui";
	private final String SWAGGER_URI_SWAGGER_RESOURCES = "/swagger-resources/**";
	private final String SWAGGER_URI_CONF_SECURITY = "/configuration/security";
	private final String SWAGGER_URI_SWAGGER_UI = "/swagger-ui.html";
	private final String SWAGGER_URI_SWAGGER_WEBJARS = "/webjars/**";


	public ServerSecurityConfiguration(FleetManagerAuthenticationEntryPoint customAuthenticationEntryPoint, @Qualifier("userService") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(SWAGGER_URI_API_DOC, SWAGGER_URI_CONF_UI, SWAGGER_URI_SWAGGER_RESOURCES, SWAGGER_URI_CONF_SECURITY, SWAGGER_URI_SWAGGER_UI,
				SWAGGER_URI_SWAGGER_WEBJARS);
	}

}
