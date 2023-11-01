package com.hrs.apigateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class SecurityConfig extends ResourceServerConfigurerAdapter {

	@Value("${zuul.routes.oauth.path}")
	private String authPath;

	@Autowired
	@Qualifier("delegatedAuthenticationEntryPoint")
	private AuthenticationEntryPoint authEntryPoint;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(authPath).permitAll().antMatchers("/**").authenticated().and()
				.exceptionHandling().authenticationEntryPoint(authEntryPoint);
	}
}
