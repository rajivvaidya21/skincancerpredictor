package com.bca.single;

import com.bca.users.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class UserProfile {

    public static String id;
      public static  String username;
      public static  String email;
      public static  String password;
      public static  String gender;
      public static  String active;
      public static  String phone;
      public static  String fname;
      public static  String lname;

    public static void setUserProfile(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        gender = user.getGender();
        active = user.getActive();
        phone = user.getPhone();
        fname= user.getFname();
        lname = user.getLname();
    }

}
