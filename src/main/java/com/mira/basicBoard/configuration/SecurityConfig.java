package com.mira.basicBoard.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        		.authorizeRequests()
        			.antMatchers("/", "/login/fail", "/login").permitAll();// 시큐리티 처리에 HttpServletRequest를 이용
//        			.anyRequest().authenticated(); // 모든 요청에 인증 필요

        http
        		.formLogin()
	        		.loginPage("/");
//	        		.loginProcessingUrl("/login")
//	        		.defaultSuccessUrl("/login/success")
//	        		.failureUrl("/login/fail")
//	        		.permitAll()
//	        		.and()
//	        	.csrf().disable()

        ; // 폼 로그인 방식 사용
    }
}
