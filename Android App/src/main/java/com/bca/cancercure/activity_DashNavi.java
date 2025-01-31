package com.bca.cancercure;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import com.bca.api.ApiUtil;
import com.bca.api.RetrofitAPI;
import com.bca.single.PrefSingleton;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_DashNavi extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    NavController navController;
    DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView txtNam, txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String tok = PrefSingleton.getInstance().read("TOKEN");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                new verifyUser().doInBackground(tok);
            }
        });

        setContentView(R.layout.activity__dash_navi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SKINN");
        drawer = findViewById(R.id.drawer_layout);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_opened, R.string.drawer_closed)
            {
                public void onDrawerClosed(View view)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = false;
                }
                public void onDrawerOpened(View drawerView)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = true;
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            drawer.addDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
        }
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_detect, R.id.nav_places,R.id.nav_tests, R.id.nav_whoatrisk,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
         navigationView.setItemIconTintList(null);
         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.nav_logout){

                    new AlertDialog.Builder(activity_DashNavi.this)
                            .setTitle("Logout")
                            .setMessage("Do you really want to Logout??")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    PrefSingleton.getInstance().writePreference("TOKEN","");
                                    RetrofitAPI.tok="";
                                    finish();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
                else  if(id == R.id.nav_home){
                    navController.navigate(R.id.nav_home);
                    drawer.closeDrawers();
                }
                else  if(id == R.id.nav_detect){
                    navController.navigate(R.id.nav_detect);
                    drawer.closeDrawers();
                }
                else  if(id == R.id.nav_tests){
                    navController.navigate(R.id.nav_tests);
                    drawer.closeDrawers();
                }
                else  if(id == R.id.nav_whoatrisk){
                    navController.navigate(R.id.nav_whoatrisk);
                    drawer.closeDrawers();
                }
                else  if(id == R.id.nav_places){
                    navController.navigate(R.id.nav_places);
                    drawer.closeDrawers();
                }
                return false;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        txtNam = headerView.findViewById(R.id.profileText1);
        txtEmail = headerView.findViewById(R.id.profileText2);
        txtNam.setText(PrefSingleton.getInstance().getName());
        txtEmail.setText(PrefSingleton.getInstance().getEMail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__dash_navi, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_tests) {

        } else if (id == R.id.nav_logout) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    private final class verifyUser extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String tok =params[0];
            ApiUtil.getServiceClass().verifyAsync(tok).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.isSuccessful()){
                        System.out.println("Im in!!!");
                    }
                    if(response.code()==401){
                        Toast.makeText(activity_DashNavi.this,"Not authorized! Logging out.", Toast.LENGTH_LONG).show();
                        PrefSingleton.getInstance().writeTokenNull();
                        Intent intent = new Intent(activity_DashNavi.this,activity_login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity_DashNavi.this.startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(activity_DashNavi.this,"Internal Server Error! Logging out.", Toast.LENGTH_LONG).show();
                    PrefSingleton.getInstance().writeTokenNull();
                    Intent intent = new Intent(activity_DashNavi.this,activity_login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity_DashNavi.this.startActivity(intent);
                }
            });
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
        }
    }




}