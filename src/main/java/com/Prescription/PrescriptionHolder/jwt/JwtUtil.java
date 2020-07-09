package com.Prescription.PrescriptionHolder.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.Prescription.PrescriptionHolder.model.User;
import com.mongodb.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	private String SECRET_KEY="Secret";
	
	public String generateToken(User user) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("email", user.getEmail());
		return createToken(claims,user.getId());
	}

	private String createToken(Map<String, Object> claims, String id) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims).setSubject(id).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+(1000*60*60*60)))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public Boolean validToken(String token,User user) {
		final String email=exteractEmail(token);
		return (email.equals(user.getEmail()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extactExpiration(token).before(new Date());
	}

	public String exteractEmail(String token) {
		Claims claims=extractAllClaims(token);
    	return (String) claims.get("email");
    }
   

	public Date extactExpiration(String token) {
    	return extractClaim(token,Claims::getExpiration);
    }
	
	 private <T>T extractClaim(String token,  Function<Claims,T> claimResolver) {
		 final Claims claims=extractAllClaims(token);
			return claimResolver.apply(claims);
		}

	public Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
}
