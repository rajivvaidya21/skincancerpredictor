package com.cancercure.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.cancercure.database.DataSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("dashboardVideos")
public class DashboardActivities {
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getVideos() {
		Map<String, Object> list = new HashMap<String,Object>();
		try {
		
			Connection con = DataSource.getConnection();
			PreparedStatement stmt = con.prepareStatement("Select name, url from videos_tbl");
			ResultSet rs = stmt.executeQuery(); 
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			while(rs.next()) {
				list.put(rs.getString(1), rs.getString(2));
			}
			String jsonText = gson.toJson(list);
			return Response
				      .status(Response.Status.OK)
				      .entity(jsonText)
				      .build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response
				      .status(Response.Status.INTERNAL_SERVER_ERROR)
				      .entity("Internal Server Errors")
				      .build();
		}
	}
}
