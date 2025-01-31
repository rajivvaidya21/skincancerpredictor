package com.cancercure.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.cancercure.config.Constants;
import com.cancercure.database.DataSource;
import com.cancercure.jwt.JWTVerifier;
import com.cancercure.model.User;
import com.google.gson.Gson;

@Path("/userLogin")
public class Login implements Constants {
	
@POST
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public Response getuserAuthService(@Context HttpHeaders header,
		@Context HttpServletResponse response,
		@FormParam("name") String name, @FormParam("pass") String pass, @FormParam("id") String id) {
    String username =name;
    String password = pass;
    String d_id = id;
    String token_jwt ="";
	try {
		User user= authenticate(username, password);
		if(user!=null) {
			JWTVerifier jwt = new JWTVerifier();
			token_jwt = jwt.createToken(username,d_id);
			System.out.println(token_jwt);
			response.setHeader("token", token_jwt);

			Gson gson = new Gson();
			String userJson = gson.toJson(user);

				System.out.println(userJson);
			return Response
				      .status(Response.Status.OK)
				      .entity(userJson)
				      .build();
		}
		else {
			return Response
				      .status(Response.Status.UNAUTHORIZED)
				      .entity("Invalid Credentials")
				      .build();
		}
	}
	catch(Exception e) {
		e.printStackTrace();
		return Response
			      .status(Response.Status.UNAUTHORIZED)
			      .entity("Invalid Credentials")
			      .build();
	}
			
}


User authenticate(String username, String password) {
	User user = null ;

	try {
		Connection con = DataSource.getConnection();
		PreparedStatement stmt = con.prepareStatement
				("Select id, username, email, gender, phone, active, firstname, lastname from users where"
						+ " username = ? and CAST(aes_decrypt(password,'"+MySQL_Key+"') as char(50) ) = ?");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery(); 
		while(rs.next()) {
			user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),
					rs.getString(8));
		}
		con.close();
		return user.getUsername()==null ? null : user;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}
}