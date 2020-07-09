package com.Prescription.PrescriptionHolder.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.NestedServletException;

import com.Prescription.PrescriptionHolder.jwt.JwtUtil;
import com.Prescription.PrescriptionHolder.model.User;
import com.Prescription.PrescriptionHolder.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	@Autowired
	UserService userService;
	@Autowired
	private JwtUtil jwt;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, ExpiredJwtException,NullPointerException ,IllegalStateException,NestedServletException {
		// TODO Auto-generated method stub
		System.out.println("****doFilterInternal***");

		String token=request.getHeader("authToken");
		String email=null;
		//String token=null;
		System.out.println(token);
		if(token!=null) {
			try {
				Claims claim=jwt.extractAllClaims(token);
				email=(String)claim.get("email");
				if(email!=null) {
					User userDetail=userService.findByEmail(email);
					if( jwt.validToken(token,userDetail)&& SecurityContextHolder.getContext().getAuthentication() == null) {
						UserDetails user=userService.loadUserByUsername(email);
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
								new UsernamePasswordAuthenticationToken(user.getUsername(), null,user.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
								.buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);		
	
					}else {
						throw new Exception();
					}
				}
			}catch(Exception e) {
				response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
				 response.getOutputStream().print("invalid");
				 response.flushBuffer();
				 return ;	
			}
		}
		filterChain.doFilter(request, response);
	}
	
	
}
