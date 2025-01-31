package com.bca.cancercure.ui.detection;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bca.api.ApiUtil;
import com.bca.cancercure.R;
import com.bca.cancercure.activity_register;
import com.bca.cancercure.ui.home.HomeFragment;
import com.bca.data.Lesion;
import com.bca.users.User;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetectionFragment extends Fragment {

    TextView resTxt;
    private DetectionModel detectionModel;
    View root=null;
    ImageView imageView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

        detectionModel = ViewModelProviders.of(this).get(DetectionModel.class);
         root = inflater.inflate(R.layout.fragment_detection, container, false);
        final TextView textView = root.findViewById(R.id.detecttextview);
        detectionModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        imageView = root.findViewById(R.id.imageView4);
        resTxt = root.findViewById(R.id.resulttxt);
        Button b = (Button) root.findViewById(R.id.capturebtn);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,1 );
            }
        });
        Button b2 = (Button) root.findViewById(R.id.choosebtn);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        resTxt.setText("");
        Bitmap image=null;
        System.out.println(resultCode);

        if(resultCode==0){
            Fragment homefrag = new HomeFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, homefrag);
            transaction.addToBackStack(null);
            transaction.commit();
            return;
        }

        if (requestCode == 1) {

            image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) root.findViewById(R.id.imageView2); //sets imageview as the bitmap
            imageview.setImageBitmap(image);
        }

        if (requestCode == 2) {
            Uri selectedImageUri = data.getData();
            ImageView imageview = (ImageView) root.findViewById(R.id.imageView2); //sets imageview as the bitmap
            imageview.setImageURI(selectedImageUri);
            Drawable drawable =  imageview.getDrawable();
            image = ((BitmapDrawable) drawable).getBitmap();
            imageview.setImageBitmap(image);
        }
        File file = null;

        OutputStream os = null;
        try {
            file = File.createTempFile("tempimage", ".jpg", getActivity().getCacheDir());
            os = new BufferedOutputStream(new FileOutputStream(file));
            image.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("imagefile", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
        imageView.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(R.drawable.loadd).into(imageView);
        ApiUtil.getServiceClass().processModel(filePart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String les = null;
                try {
                    les = response.body().string();
                    //jsonToMap(les);  //Got result map here, display!

                    Type stringStringMap = new TypeToken<Map<String, Object>>(){}.getType();
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    Map<String,Object> map = gson.fromJson(les, stringStringMap);
                    System.out.println(map);
                    String result="";
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String r = entry.getKey()+":"+entry.getValue();
                        result+=r+"\n";
                        System.out.println(r);
                    }
                    resTxt.setText( result);
                    imageView.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                    imageView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                resTxt.setText(t.getMessage().toString());
                System.out.println(t.getCause());
                System.out.println(t.getMessage());
                imageView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public static Map jsonToMap(String t) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        JSONArray jsonarray = new JSONArray(t);
        for(int i=0; i < jsonarray.length(); i++) {
            String hhh = jsonarray.get(i).toString();
            String[] tokens = hhh.split(",");
            map.put(tokens[0].substring(2,tokens[0].length()-2),tokens[1].substring(1,tokens[1].length()-3));
        }
        System.out.println("map : "+map);
        return map;
    }
}