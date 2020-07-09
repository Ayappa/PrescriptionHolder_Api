package com.Prescription.PrescriptionHolder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.Prescription.PrescriptionHolder.jwt.JwtBean;
import com.Prescription.PrescriptionHolder.jwt.JwtUtil;
import com.Prescription.PrescriptionHolder.model.User;
import com.Prescription.PrescriptionHolder.service.UserService;

import io.jsonwebtoken.Claims;

@RestController
@CrossOrigin(origins="https://prescriptionholder.herokuapp.com")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired 
	private JwtUtil jwt;
	@Autowired
	private AuthenticationManager authenticationManagerBean;
	
	@PostMapping("/registerUser")
	public JwtBean register(@RequestBody User user) {
		if(userService.findByEmail(user.getEmail())!=null){
			final JwtBean jwtToken=new JwtBean("alreadyExists");
			return jwtToken;
		}else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User userDetails=userService.save(user);
			String toekn=jwt.generateToken(userDetails);
			final JwtBean jwtToken=new JwtBean(toekn);
			return jwtToken;
		}
	}
	///authentiCATION MANAGER CALLS authentication provider which handles type of authentication  , specified in configure in authConfig
	///"auth.userDetailsService(userDetailsService);" this intern calls userService which implements UserDetailsService and ,
	//override "loadUserByUsername" with calls db to check the given details are present in db or throws error
	@PostMapping("/login")
	public JwtBean login(@RequestBody User user) throws Exception{
		User userCheck=userService.findByEmail(user.getEmail());
		try {
			authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
			}
			catch(BadCredentialsException e) {
				final JwtBean jwtToken=new JwtBean("Incorrect creditional");
				return jwtToken;
			}
		String toekn=jwt.generateToken(userCheck);
		final JwtBean jwtToken=new JwtBean(toekn);
		return jwtToken;
	}
	
	@PostMapping("/updatePassword")
	public boolean updatePassword(@RequestBody User user,@RequestHeader("authToken") String authToken) {
		System.out.println("****updatePassword***");
		Claims claim=jwt.extractAllClaims(authToken);
		String email=(String)claim.get("email");
		User userCheck=userService.findByEmail(email);
		//System.out.println(userCheck.toString());

		if(userCheck==null || !jwt.validToken(authToken, userCheck) ) {
			final JwtBean jwtToken=new JwtBean("alreadyExists or invalid");
			return false;
		}else {
		//User userCheck=userservice.findUserByEmail(user.getEmail());
			userCheck.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.save(userCheck);
			 return true;	
		}
		
	}
}
