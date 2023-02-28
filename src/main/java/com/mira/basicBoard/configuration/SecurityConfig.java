package com.mira.basicBoard.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.mira.basicBoard.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UserService userService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http	.csrf().disable();
        http
        		.authorizeRequests()
        		.antMatchers("/", "/login/**", "/enroll/**", "/css/**", "/javaScript/**").permitAll()
        		.antMatchers(HttpMethod.GET, "/board/**").permitAll()
        		.antMatchers(HttpMethod.POST, "/board/**").hasAnyRole("USER")
        		.antMatchers(HttpMethod.PUT, "/board/**").hasAnyRole("USER")
        		.antMatchers(HttpMethod.DELETE, "/board/**").hasAnyRole("USER")
        		.anyRequest().authenticated();
        
        http
        		.formLogin()
	        		.loginPage("/")
	        		.loginProcessingUrl("/login/process")
	        		.usernameParameter("userId")
	        		.passwordParameter("userPwd")
	        		.defaultSuccessUrl("/board/list")
//	        		.failureUrl("/login/fail") -> 핸들러에서 처리해주는게 나을 것 같은데... 수정!!!!!!
	        		.failureHandler(new AuthenticationFailureHandler() {
						@Override
						public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
								AuthenticationException exception) throws IOException, ServletException {
							log.debug("getMessage : " + exception.getMessage());
							log.debug("getCause : " + exception.getCause());
							log.debug("getStackTrace : " + exception.getStackTrace());
							log.debug("getLocalizedMessage : " + exception.getLocalizedMessage());
							log.debug("getSuppressed : " + exception.getSuppressed());
							
	                        response.sendRedirect("/login/fail");
						}
	        		});
        http	.exceptionHandling()
        			.accessDeniedPage("/error/405");
    }
    
    //사용자인증처리
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
