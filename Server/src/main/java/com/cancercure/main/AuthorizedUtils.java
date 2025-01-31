package com.cancercure.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cancercure.jwt.JWTVerifier;

import io.jsonwebtoken.JwtException;

@Path("/authorization")
public class AuthorizedUtils {
	
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUtils(@Context HttpHeaders httpheaders) {
		
		//if jwt verified
		
		 String token = httpheaders.getHeaderString("token");
			JWTVerifier jwt = new JWTVerifier();
			String name ="";
				try {
				    name = jwt.verifyGetName(token);
				}
				catch (JwtException ex) {       // (5)
				    // we *cannot* use the JWT as intended by its creator
					return Response
						      .status(Response.Status.UNAUTHORIZED).entity("Not Authorized").build();
				}
				if(name ==null) {
				
					return Response
						      .status(Response.Status.UNAUTHORIZED).entity("Not Authorized").build();
				}
				return Response
					      .status(Response.Status.OK).entity("Hello  "+ name).build();
	}

}
