package com.mira.basicBoard.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mira.basicBoard.service.UserService;

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
        			.antMatchers("/", "/login/**", "/enroll/**", "/board/{boardNo}", "/css/**", "/javaScript/**").permitAll()
//        			.antMatchers("/board/").hasAnyRole("USER");
        			.anyRequest().hasAnyRole("USER");
//        			.anyRequest().authenticated(); // 모든 요청에 인증 필요
        
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
//	                        System.out.println("getMessage : " + exception.getMessage());
//	                        System.out.println("getCause : " + exception.getCause());
//	                        System.out.println("getStackTrace : " + exception.getStackTrace());
//	                        System.out.println("getLocalizedMessage : " + exception.getLocalizedMessage());
//	                        System.out.println("getSuppressed : " + exception.getSuppressed());
	                        response.sendRedirect("/login/fail");
						}
	        		});

    }
    
    //사용자인증처리
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
