package com.bca.cancercure.ui.whosatrisk;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bca.cancercure.R;

import java.io.InputStream;

public class whoatriskFragment extends Fragment {

    private whoatriskViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(whoatriskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_whosatrisk, container, false);
        TextView txt3 = root.findViewById(R.id.textView3);
        txt3 = root.findViewById(R.id.textView3);
        txt3.setMovementMethod(new ScrollingMovementMethod());
        try {
            Resources res = getResources(); InputStream in_s = res.openRawResource(R.raw.whosatrisk);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            txt3.setText(new String(b));
        } catch (Exception e) {
            txt3.setText("Error: can't display text.");
        }



        return root;
    }
}