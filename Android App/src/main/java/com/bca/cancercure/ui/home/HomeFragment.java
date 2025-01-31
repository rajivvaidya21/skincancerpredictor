package com.bca.cancercure.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bca.api.ApiUtil;
import com.bca.cancercure.R;
import com.bca.cancercure.activity_login;
import com.bca.cancercure.ui.places.NearbyPlaces;
import com.bca.single.PrefSingleton;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    TextView txt1,txt2,txt3, imgtext1, imgtext2;
    ImageView imgv1, imgv2, imgv3, imageRolldash, blogimg1, blogimg2;
    boolean gpsEnbl = false;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ArrayList<String> videosUrl =  new ArrayList<String>();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkLocationPermission();
       // checkGps();
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        txt1 = root.findViewById(R.id.title);
        txt2 = root.findViewById(R.id.title2);
        txt3 = root.findViewById(R.id.title3);

        imgv1 = root.findViewById(R.id.image);
        imgv2 = root.findViewById(R.id.image2);
        imgv3 = root.findViewById(R.id.image3);
        imageRolldash = root.findViewById(R.id.imageRolldash);
        imgtext1 = root.findViewById(R.id.myImageViewText);
        imgtext2 = root.findViewById(R.id.myImageViewText2);

        blogimg1 = root.findViewById(R.id.blogimg);
        blogimg2 = root.findViewById(R.id.blogimg2);
        imgv1.setVisibility(View.INVISIBLE);
        imgv2.setVisibility(View.INVISIBLE);
        imgv3.setVisibility(View.INVISIBLE);
        txt1.setVisibility(View.INVISIBLE);
        txt1.setVisibility(View.INVISIBLE);
        txt1.setVisibility(View.INVISIBLE);

        Glide.with(getActivity()).load(R.drawable.loadd).into(imageRolldash);
        ApiUtil.getServiceClass().getVideos().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // System.out.println(res);

                String[] keyValue = res.split(",");

                System.out.println(Arrays.toString(keyValue));
                Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType();
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                Map<String,Object> map = gson.fromJson(res, stringStringMap);
                System.out.println(map);
                List<TextView> textViews = new ArrayList<TextView>();
                textViews.add(txt1);
                textViews.add(txt2);
                textViews.add(txt3);
                List<ImageView> imgViews = new ArrayList<ImageView>();
                imgViews.add(imgv1);
                imgViews.add(imgv2);
                imgViews.add(imgv3);
                int i=0;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    textViews.get(i).setText(entry.getKey());
                    videosUrl.add(entry.getValue().toString());
                    Uri uri = Uri.parse(entry.getValue().toString());
                    String videoID = uri.getQueryParameter("v");
                    String url = "https://img.youtube.com/vi/" + videoID +"/maxresdefault.jpg";
                    Glide.with(getActivity()).load(url).into(imgViews.get(i));
                    i++;
                    //System.out.println(entry.getKey() + "/" + entry.getValue());
                }
                imageRolldash.setVisibility(View.INVISIBLE);
                imgv1.setVisibility(View.VISIBLE);
                imgv2.setVisibility(View.VISIBLE);
                imgv3.setVisibility(View.VISIBLE);
                txt1.setVisibility(View.VISIBLE);
                txt1.setVisibility(View.VISIBLE);
                txt1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                imageRolldash.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Failed to get video details + \n ", Toast.LENGTH_SHORT).show();
            }
        });



        imgv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videosUrl.get(0))));
            }

        });


        imgv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videosUrl.get(1))));
            }

        });

        imgv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videosUrl.get(2))));
            }

        });

        blogimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skincancer.org/blog/cold-dry-air-requires-a-little-extra-skin-care/")));
            }

        });

        blogimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skincancer.org/blog/ask-the-expert-do-i-have-to-wear-sunscreen-under-my-mask/")));
            }

        });


        ApiUtil.getServiceClass().getInformation().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String res = response.body().string();
                    Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType();
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    Map<String,String> map = gson.fromJson(res, stringStringMap);
                    Map.Entry<String,String> entry = map.entrySet().iterator().next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imgtext1.setText(key);
                    imgtext2.setText(value);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Failed to get information + \n ", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }


    public void checkGps(){
        if (!gpsEnbl) {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Enable GPS")
                    .setPositiveButton("Open Setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                  // .setNegativeButton("Cancel", null)
                    .show();
        }
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Allow Location Access")
                        .setMessage("Enable Location Access to see places")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}