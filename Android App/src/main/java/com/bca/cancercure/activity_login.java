package com.bca.cancercure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bca.api.ApiUtil;
import com.bca.api.RetrofitAPI;
import com.bca.single.PrefSingleton;
import com.bca.users.User;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_login extends AppCompatActivity {

    private final String TAG = activity_login.class.getSimpleName();
     LinearLayout loginLayout;
    Button regButton, loginBtn ;
    ImageView imageView;
    EditText uEdit, pEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        PrefSingleton.getInstance().Initialize(getApplicationContext());


        if(!PrefSingleton.getInstance().read("TOKEN").isEmpty()){
            Intent dash = new Intent(activity_login.this, activity_DashNavi.class);
            activity_login.this.startActivity(dash);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         imageView = (ImageView) findViewById(R.id.imageRoll);
        regButton = findViewById(R.id.regBtn);
        uEdit = findViewById(R.id.loginUsername);
        pEdit = findViewById(R.id.loginPassword);
        loginLayout  = (LinearLayout) findViewById(R.id.linearLay1);

        loginBtn = findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username, password;
                username = uEdit.getText().toString().trim();
                password = pEdit.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(activity_login.this, "Fill all the details!",Toast.LENGTH_SHORT).show();
                    return;
                }
                enableLayout(false);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(activity_login.this).load(R.drawable.loadd).into(imageView);
                ApiUtil.getServiceClass().login(username,password).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        try {
                            User user = response.body();
                            PrefSingleton.getInstance().setNameEmail(user.getFname()+" "+ user.getLname(), user.getEmail());
                            System.out.println();
                        } catch (Exception e) {
                            e.printStackTrace();
                           // Toast.makeText(activity_login.this,e.toString(),Toast.LENGTH_LONG).show();
                        }

                        if(response.isSuccessful()){
                            String token = response.headers().get("token");
                            Log.d(TAG,token);

                            PrefSingleton.getInstance().writePreference("TOKEN",token);
                            RetrofitAPI.tok=token;
                            Intent dash = new Intent(activity_login.this, activity_DashNavi.class);
                            //dash.putExtra("token", token);
                            activity_login.this.startActivity(dash);
                            enableLayout(true);
                            imageView.setVisibility(View.INVISIBLE);
                            finish();
                        }
                        else if(response.code()==401){
                            Log.d(TAG, response.message());
                            enableLayout(true);
                            imageView.setVisibility(View.INVISIBLE);
                            Toast.makeText(activity_login.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d(TAG, response.message());
                            enableLayout(true);
                            imageView.setVisibility(View.INVISIBLE);
                            Toast.makeText(activity_login.this,"Invalid Response",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        imageView.setVisibility(View.INVISIBLE);
                        enableLayout(true);
                        System.out.println(t.getCause());
                        Toast.makeText(activity_login.this,"Server Unreachable!",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regInent = new Intent(activity_login.this, activity_register.class);
                //regInent.putExtra("key", value); //Optional parameters
                activity_login.this.startActivity(regInent);
            }
        });
    }

    public void enableLayout(Boolean flag){
        for ( int i = 0; i < loginLayout.getChildCount();  i++ ){
            View view = loginLayout.getChildAt(i);
            view.setEnabled(flag);
        }
    }
}