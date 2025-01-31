package com.bca.cancercure.ui.diagtests;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bca.cancercure.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;

public class mri_activity extends AppCompatActivity {


TextView txt ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        char id = intent.getCharExtra("id",'d');

        setContentView(R.layout.genetic_activity);
        txt = findViewById(R.id.geneticTextview);

        txt.setMovementMethod(new ScrollingMovementMethod());
        try {
            int resid = 'd';
            switch(id){
                case 'm': resid = R.raw.mri;
                break;
                case 'x': resid = R.raw.xray;
                break;
                case 'c': resid = R.raw.ct;
                break;
            }
            Resources res = getResources(); InputStream in_s = res.openRawResource(resid);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            txt.setText(new String(b));
        } catch (Exception e) {
            txt.setText("Error: can't show.");
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
