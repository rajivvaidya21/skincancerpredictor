package com.bca.api;


import com.bca.data.Lesion;
import com.bca.users.User;

import java.io.File;
import java.util.List;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("register")
    Call<String> register(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("username") String username,
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("gender") String gender,
            @Field("phone") String phone,
            @Field("country") String country,
            @Field("dob") String dob
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("userLogin")
    Call<User> login(
            @Field("name") String username,
            @Field("pass") String password
    );

    @FormUrlEncoded
    @POST("verifyAsync")
    Call<Boolean> verifyAsync(
            @Field("token") String token
    );


    @Multipart
    @POST("processupload")
    Call<ResponseBody> processModel(
            @Part MultipartBody.Part filePart
    );



    @POST("dashboardVideos")
    Call<ResponseBody> getVideos();


    @POST("information")
    Call<ResponseBody> getInformation();
    }

