package com.bca.single;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefSingleton{
    private static PrefSingleton mInstance;
    private Context mContext;
    //
    private SharedPreferences mMyPreferences;

    private PrefSingleton(){ }

    public static PrefSingleton getInstance(){
        if (mInstance == null) mInstance = new PrefSingleton();
        return mInstance;
    }

    public void Initialize(Context ctxt){
        mContext = ctxt;
        //
        mMyPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void writePreference(String key, String value){
        SharedPreferences.Editor e = mMyPreferences.edit();
        e.putString(key, value);
        e.commit();
    }

    public String read(String key){

        return mMyPreferences.getString(key, "");
    }

    public void writeTokenNull(){
        SharedPreferences.Editor e = mMyPreferences.edit();
        e.putString("TOKEN", "");
        e.commit();
    }


    public void setNameEmail(String name, String email){
        SharedPreferences.Editor e = mMyPreferences.edit();
        e.putString("name", name);
        e.putString("email", email);
        e.commit();
    }

    public String getName(){

        return mMyPreferences.getString("name", "");
    }

    public String getEMail(){

        return mMyPreferences.getString("email", "");
    }
}