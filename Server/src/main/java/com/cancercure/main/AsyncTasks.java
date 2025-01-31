package com.cancercure.main;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.cancercure.jwt.JWTVerifier;

@Path("verifyAsync")
public class AsyncTasks {
	@POST
	public Response verifyUser(@FormParam("token") String token) {
		System.out.println(token);
		JWTVerifier ver = new JWTVerifier();
		if(ver.verify(token)) {
			return Response.ok().entity("Verified").build();
		}
		else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}