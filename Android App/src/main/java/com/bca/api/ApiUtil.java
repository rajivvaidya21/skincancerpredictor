package com.bca.api;


import com.bca.data.Constants;

public class ApiUtil implements Constants {
    public static Api getServiceClass(){
        return RetrofitAPI.getRetrofit(URL).create(Api.class);
    }
}
