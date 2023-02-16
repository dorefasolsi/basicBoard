package com.mira.basicBoard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        			.antMatchers("/", "/login/**", "/enroll/**").permitAll();// 시큐리티 처리에 HttpServletRequest를 이용
//        			.anyRequest().authenticated(); // 모든 요청에 인증 필요

        http
        		.formLogin()
	        		.loginPage("/")
	        		.loginProcessingUrl("/login/process")
//	        		.defaultSuccessUrl("/login/success")
//	        		.failureUrl("/login/fail")
//	        		.permitAll()
//	        		.and()
	        		
        ; // 폼 로그인 방식 사용
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
