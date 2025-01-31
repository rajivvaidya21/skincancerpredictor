package com.cancercure.model;

public class User {

    private String id;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String active;
    private String phone;
    private String country;
    private String dob;
    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	private String fname;
    private String lname;

    public User(String name, String email, String password, String gender) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }



    public User(String id, String name, String email, String gender,String phone, String active, String fname, String lname) {
        this.id = id;
        this.username = name;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.active = active;
        this.fname = fname;
        this.lname=lname;
    }


    public User(String fname, String lname, String username, String pass, String email, String gender,String phone, String country, String dob ) {
        
        this.username = username;
        this.password=pass;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.fname = fname;
        this.lname=lname;
        this.country=country;
        this.dob = dob;
    }

    public User(String email, String pass) {
    	 this.email = email;
         this.password=pass;
    	
    }
    
    public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
        return id;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getGender() {
        return gender;
    }
}