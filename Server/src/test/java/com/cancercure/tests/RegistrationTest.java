package com.cancercure.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.cancercure.model.User;

public class RegistrationTest {

	 @Test
	  public void register() {
	        System.out.println("This is the test case in registration class");
	        String fname="test1",  lname="v",
			 username="test1v",  pass="pass",
			 email="test1@gmail.com", gender="Male", phone="777766666",
			 country="India", dob="1995-01-10";
	        User us = new User(fname, lname, username,pass,email,gender,phone,country,dob);
	        assertEquals("test1", us.getFname());
	        assertEquals("v", us.getLname());
	        assertEquals("test1v", us.getUsername());
	        assertEquals("pass", us.getPassword());
	        assertEquals("test1@gmail.com", us.getEmail());
	        assertEquals("Male", us.getGender());
	        assertEquals("777766666", us.getPhone());
	        assertEquals("India", us.getCountry());
	        assertEquals("1995-01-10", us.getDob());
	    }
	
}
