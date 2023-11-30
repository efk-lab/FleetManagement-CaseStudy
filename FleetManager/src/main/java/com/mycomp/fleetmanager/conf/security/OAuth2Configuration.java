package com.mycomp.fleetmanager.conf.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

   private final AuthenticationManager authenticationManager;

   private final PasswordEncoder passwordEncoder;

   private final UserDetailsService userService;
   
   private final String CLIENT_SCOPE_WRITE = "write";
   
   private final String CLIENT_SCOPE_READ = "read";
   
   private final String CLIENT_RESOURCE_ID = "fleetmanagerApi";

   @Value("${spring.security.jwt.clientId}")
   private String clientId;

   @Value("${spring.security.jwt.clientSecret}")
   private String clientSecret;

   @Value("${spring.security.jwt.signingKey}")
   private String jwtSigningKey;

   @Value("${spring.security.jwt.accessTokenValidititySeconds}")
   private int accessTokenValiditySeconds;

   @Value("${spring.security.jwt.authorizedGrantTypes}")
   private String[] authorizedGrantTypes;

   @Value("${spring.security.jwt.refreshTokenValiditySeconds}")
   private int refreshTokenValiditySeconds;
   

   public OAuth2Configuration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDetailsService userService) {
       this.authenticationManager = authenticationManager;
       this.passwordEncoder = passwordEncoder;
       this.userService = userService;
   }

   @Override
   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       clients.inMemory()
               .withClient(clientId)
               .secret(passwordEncoder.encode(clientSecret))
               .accessTokenValiditySeconds(accessTokenValiditySeconds)
               .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
               .authorizedGrantTypes(authorizedGrantTypes)
               .scopes(CLIENT_SCOPE_READ, CLIENT_SCOPE_WRITE)
               .resourceIds(CLIENT_RESOURCE_ID);
   }

   @Override
   public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
       endpoints
               .accessTokenConverter(accessTokenConverter())
               .userDetailsService(userService)
               .authenticationManager(authenticationManager);
   }

   @Bean
   JwtAccessTokenConverter accessTokenConverter() {
       JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       return converter;
   }
  

}