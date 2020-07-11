
package com.Prescription.PrescriptionHolder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.Prescription.PrescriptionHolder.filter.JwtRequestFilter;

@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter   implements WebMvcConfigurer {
  
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
  @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.httpBasic()
		.and().exceptionHandling()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.formLogin().disable()
		.authorizeRequests()
		.antMatchers("/login","/registerUser","/updatePassword").permitAll()
		.anyRequest().authenticated() .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
  
  @Bean 
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
  @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
  
  @Bean
	public PasswordEncoder getPasswordEncoder() {
		//return  NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
}
