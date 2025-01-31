package com.cancercure.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cancercure.config.Constants;
import com.cancercure.database.DataSource;

@Path("/register")
public class Registration implements Constants {
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response registration(
			@FormParam("fname") String fname, @FormParam("lname") String lname,
			@FormParam("username") String username, @FormParam("pass") String pass, 
			@FormParam("email") String email,
			@FormParam("gender") String gender,
			@FormParam("phone") String phone,
			@FormParam("country") String country,
			@FormParam("dob") String dob) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date=sdf.format(new Date());
				Connection con = DataSource.getConnection();
				PreparedStatement stmt = con.prepareStatement
						("INSERT INTO users(firstname, lastname, username, password, "
						+ " email, gender, phone, country, dob, upddat, updtim) VALUES (?, ?, ?,AES_ENCRYPT(?,?),?,?,?,?,?,?,?)");
				stmt.setString(1, fname);
				stmt.setString(2, lname);
				stmt.setString(3, username);
				stmt.setString(4, pass);
				stmt.setString(5, MySQL_Key);
				stmt.setString(6, email);
				stmt.setString(7, gender);
				stmt.setString(8, phone);
				stmt.setString(9, country);
				stmt.setString(10, dob);
				stmt.setString(11, date);
				stmt.setInt(12, 0);
				stmt.executeUpdate();
				return Response
					      .status(Response.Status.OK).entity("Registered Succesfully!").build();
			} 
			catch (SQLException e) {
				if(e.getMessage().contains("username_UNIQUE")) {
					return Response
						      .status(Response.Status.CONFLICT).entity("Username Already Exists").build();
				}
				e.printStackTrace();
				return Response
					      .status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error").build();
			}
	}
}