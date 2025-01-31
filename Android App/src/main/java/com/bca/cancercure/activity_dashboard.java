package com.bca.cancercure;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bca.api.ApiUtil;
import com.bca.api.RetrofitAPI;
import com.bca.single.PrefSingleton;

public class activity_dashboard extends AppCompatActivity {

    TextView txtv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txtv = findViewById(R.id.textView2);
        String tok = PrefSingleton.getInstance().read("TOKEN");
        txtv.setText(tok);



    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
