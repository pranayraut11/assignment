package com.amdocs.media.assignement.authorization.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amdocs.media.assignement.authorization.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/h2-console/**", "/generate","/profile").permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		/*
		 * http.authorizeRequests().antMatchers("/profile/**").authenticated().and().
		 * formLogin()
		 * .loginProcessingUrl("/login").usernameParameter("username").passwordParameter
		 * ("password") .successHandler(this::loginSuccessHandler).failureHandler(this::
		 * loginFailureHandler).and().logout() .logoutUrl("/logout") // the URL on which
		 * the clients should post if they want to logout
		 * .logoutSuccessHandler(this::logoutSuccessHandler).invalidateHttpSession(true)
		 * ;// default response if the // client wants to get a // resource unauthorized
		 */
	}

	private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		response.setStatus(HttpStatus.OK.value());
	}

	private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}

	private void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		response.setStatus(HttpStatus.OK.value());
	}

}
