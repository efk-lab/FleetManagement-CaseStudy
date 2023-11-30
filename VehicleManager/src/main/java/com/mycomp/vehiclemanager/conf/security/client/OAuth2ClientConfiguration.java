package com.mycomp.vehiclemanager.conf.security.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@Configuration
public class OAuth2ClientConfiguration {

	@Value("${spring.security.oauth2.client.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.access-token-uri}")
	private String clientTokenUrl;

	@Value("${spring.security.oauth2.client.grant-type}")
	private String grantType;

	@Value("${spring.security..oauth2.client.scope}")
	private String scope;
	
	@Value("${spring.security.oauth2.client.user-name}")
	private String username;

	@Value("${spring.security..oauth2.client.password}")
	private String password;
	
	@Value("${spring.security.oauth2.client.resource-id}")
	private String resourceId;

	@Value("${spring.security.oauth2.client.token-name}")
	private String tokenName;
	
	
	@Bean
	protected OAuth2ProtectedResourceDetails resource() {

		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

		List<String> scopes = new ArrayList<String>();
		scopes.add(scope);

		resource.setAccessTokenUri(clientTokenUrl);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setGrantType(grantType);
		resource.setScope(scopes);
		resource.setUsername(username);
		resource.setPassword(password);
		resource.setId(resourceId);
		resource.setTokenName(tokenName);
		resource.setClientAuthenticationScheme(AuthenticationScheme.header);
		
		return resource;
		
	}

	@Bean
	public OAuth2RestOperations oauth2RestTemplate() {
		
		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
		
        OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(resource(), clientContext);
        oauth2RestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

		return oauth2RestTemplate;
		
	}
}