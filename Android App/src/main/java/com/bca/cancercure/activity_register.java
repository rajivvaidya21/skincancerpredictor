package com.bca.cancercure;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import com.bca.api.ApiUtil;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_register extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    private final String TAG = activity_register.class.getSimpleName();
    String genders[] = {"Female", "Male"};
    Spinner sp, countrySpinner;
    String fname, lname, email, password, phone, dob;
    ImageView imageView;
    EditText dobEt, fnameEt, lnameEt, emailEt, passEt, phoneEt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sp = (Spinner)findViewById(R.id.spinner1);
        countrySpinner = (Spinner)findViewById(R.id.countrySpinner);
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        String locale ="India";
        Collections.sort(countries);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, countries);
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setSelection(countryAdapter.getPosition(locale));
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_item,genders);
        //Setting the ArrayAdapter data on the Spinner
        sp.setAdapter(aa);
        sp.setSelection(0);
        Button regBtn = findViewById(R.id.registerBtn);

        imageView = (ImageView) findViewById(R.id.loadd);
        dobEt = findViewById(R.id.dob);
        fnameEt= findViewById(R.id.fname);
        lnameEt= findViewById(R.id.lname);
        emailEt= findViewById(R.id.email);
        passEt= findViewById(R.id.pass);
        phoneEt= findViewById(R.id.phno);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);


        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, activity_register.this, year, month, day);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regBtn.setEnabled(Boolean.FALSE);
                String gender = sp.getSelectedItem().toString().trim();
                String country = countrySpinner.getSelectedItem().toString().trim();
                String dob = dobEt.getText().toString().trim();
                String fname = fnameEt.getText().toString().trim();
                String lname = lnameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                String pass = passEt.getText().toString().trim();
                String phone = phoneEt.getText().toString().trim();

                if(gender.isEmpty()||country.isEmpty()|| dob.isEmpty()||fname.isEmpty()||lname.isEmpty()||email.isEmpty()||
                pass.isEmpty()||phone.isEmpty()){

                    Toast.makeText(activity_register.this,"Please fill all the details!",Toast.LENGTH_LONG).show();
                    return;
                }

                imageView.setVisibility(View.VISIBLE);
                Glide.with(activity_register.this).load(R.drawable.loadd).into(imageView);
                ApiUtil.getServiceClass().register(
                        fname, lname, email, email, pass, gender, phone,country, dob
                ).enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            String res = response.body();
                            Log.d(TAG, res);
                                Toast.makeText(activity_register.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                          Intent intent = new Intent(activity_register.this,activity_login.class);
                          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          activity_register.this.startActivity(intent);
                            imageView.setVisibility(View.INVISIBLE);
                            regBtn.setEnabled(Boolean.TRUE);
                            //loadingProgressBar.setVisibility(View.INVISIBLE);
                        }
                        else if(response.code()==409){
                            Log.d(TAG, response.message());
                            Toast.makeText(activity_register.this,"Username Already Exists!",Toast.LENGTH_LONG).show();
                            imageView.setVisibility(View.INVISIBLE);
                            regBtn.setEnabled(Boolean.TRUE);
                        }
                        else{
                            Log.d(TAG, response.message());
                            Toast.makeText(activity_register.this,"Invalid Response",Toast.LENGTH_LONG).show();
                            imageView.setVisibility(View.INVISIBLE);
                            regBtn.setEnabled(Boolean.TRUE);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //showErrorMessage();
                        Log.d(TAG, t.getMessage());
                        String msg ="Error connecting to server";
                        Log.d(TAG, msg);
                        Toast.makeText(activity_register.this,msg,Toast.LENGTH_SHORT).show();
                        imageView.setVisibility(View.INVISIBLE);
                        regBtn.setEnabled(Boolean.TRUE);
                        //loadingProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


        dobEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int days=6570;
        Date date1;
        Date date2;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, dayOfMonth);
        date1= newDate.getTime();
        date2 = new Date();
        String dob= simpleDateFormat.format(newDate.getTime());
        long duration  =  date2.getTime() - date1.getTime() ;
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);

        if(diffInDays>=days){
            dobEt.setText(dob);
        }
        else
        {
        Toast.makeText(this,"You must be over 18 years old", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(activity_register.this,activity_login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity_register.this.startActivity(intent);
    }


}
