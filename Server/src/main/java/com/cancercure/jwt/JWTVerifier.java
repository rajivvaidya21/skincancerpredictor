package com.cancercure.jwt;

import java.security.Key;
import java.util.Date;
import javax.ws.rs.core.Response;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

public class JWTVerifier {
	static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	static int expirationMinutes = 180; //3hours
	public boolean verify(String token) {
		
		try {
		    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		    //OK, we can trust this JWT
		    return true;
		} catch (JwtException e) {
			sendEXPResponse();
		}
		return false;
	}
	
	
	public String createToken(String name, String deviceID) {
		try {
			return Jwts.builder().setSubject(name+deviceID).signWith(key)
					.setExpiration(new Date(System.currentTimeMillis() + (expirationMinutes * 60 * 1000))) 
					.claim("name", name)
					.compact();
		} catch (InvalidKeyException e) {
			
		}
		return "";
	}	
	
	public Response sendEXPResponse() {
		
		return Response.status(Response.Status.UNAUTHORIZED).entity("Session Expired").build();
	}
	
	
public String verifyGetName(String token) {
		
		try {
		    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("name").toString();
		    
		} catch (JwtException e) {
			sendEXPResponse();
		}
		return null;
	}
}