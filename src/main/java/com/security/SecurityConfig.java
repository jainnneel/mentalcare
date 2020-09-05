package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private UserServices userService;
	
	public SecurityConfig(UserServices userService) {
		super();
		this.userService = userService;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http 
//		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	            .and()
				.authorizeRequests()
					.antMatchers("/register").permitAll()
					.antMatchers("/").permitAll()
					.antMatchers("/dashbord").authenticated()
					.antMatchers("/postcontent").authenticated()
					.antMatchers("/post/**").authenticated()
					.antMatchers("/updatepost").authenticated()
					.antMatchers("/userpost").authenticated()
					.antMatchers("response").authenticated()
				.and()
				
				.csrf().disable()
				.formLogin()
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/")
				.and()
					.logout()
					.invalidateHttpSession(true)
				    .deleteCookies("JSESSIONID")
				    .clearAuthentication(true)
				    .logoutRequestMatcher( new AntPathRequestMatcher("/logout"))
				    .logoutSuccessUrl("/login?logout").permitAll()
				.and()
					.rememberMe()
				.and()
					.sessionManagement()                          
					.maximumSessions(1)                         
			        .maxSessionsPreventsLogin(false)          
			        .expiredUrl("/login?expired");             
			            
	}
	
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider d = new  DaoAuthenticationProvider();
		d.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		d.setUserDetailsService(userService);
		return d;
	}
}
