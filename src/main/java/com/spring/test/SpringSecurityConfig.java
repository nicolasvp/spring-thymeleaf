package com.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/css/**","/js/**","/images/**","/plugins/**","/less/**","/bootstrap/**").permitAll()
		.antMatchers("/show/**").hasAnyRole("user")
		.antMatchers("/uploads/**").hasAnyRole("user")
		.antMatchers("/form/**").hasAnyRole("admin")
		.antMatchers("/delete/**").hasAnyRole("admin")
		.antMatchers("/bill/**").hasAnyRole("admin")
		.antMatchers("/edit/**").hasAnyRole("admin")
		.antMatchers("/create/**").hasAnyRole("admin")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/403");
		
	}
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		build.inMemoryAuthentication()
		.withUser(users.username("admin").password("123123").roles("admin","user"))
		.withUser(users.username("nicolas").password("123123").roles("user"));
		
	}
	
}
